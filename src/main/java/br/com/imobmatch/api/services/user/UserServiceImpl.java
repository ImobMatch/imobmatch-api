package br.com.imobmatch.api.services.user;

import br.com.imobmatch.api.dtos.email.RequestValidationEmailResponseDTO;
import br.com.imobmatch.api.dtos.email.RequestValidationEmailDTO;
import br.com.imobmatch.api.dtos.email.ValidateEmailRequestDTO;
import br.com.imobmatch.api.dtos.email.ValidateEmailResponseDTO;
import br.com.imobmatch.api.dtos.password.RequestPasswordResetDTO;
import br.com.imobmatch.api.dtos.password.ResetPasswordDTO;
import br.com.imobmatch.api.dtos.password.StatusPasswordResetDTO;
import br.com.imobmatch.api.dtos.user.UploadProfileImageResponse;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.auth.AuthenticationException;
import br.com.imobmatch.api.exceptions.email.ErroSendEmailException;
import br.com.imobmatch.api.exceptions.email.InvalidCodeException;
import br.com.imobmatch.api.exceptions.email.RequestCodeExpiredException;
import br.com.imobmatch.api.exceptions.email.RequestNotFoundException;
import br.com.imobmatch.api.exceptions.user.UserExistsException;
import br.com.imobmatch.api.exceptions.user.UserNotFoundException;
import br.com.imobmatch.api.infra.email.services.EmailService;
import br.com.imobmatch.api.infra.s3.service.S3Service;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.user.UserVerificationCode;
import br.com.imobmatch.api.models.enums.UserRole;
import br.com.imobmatch.api.models.enums.VerificationType;
import br.com.imobmatch.api.repositories.UserRepository;
import br.com.imobmatch.api.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Comparator;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import br.com.imobmatch.api.repositories.UserVerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder cryptPasswordEncoder;
    private final UserVerificationCodeRepository userVerificationRepository;
    private final EmailService emailService;
    private final S3Service s3Service;

    @Value("${user.email.verified.default}")
    private boolean defaultEmailVerified;

    @Override
    public UserResponseDTO getMe() {
        User user = getMeUserAuthentication();
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getProfileKey()
        );
    }

    @Override
    public byte[] downloadProfileME() {
        User user = getMeUserAuthentication();
        return this.s3Service.downloadProfilePhoto(user.getProfileKey());
    }

    @Override
    public byte[] downloadProfile(String key) {
        getMeUserAuthentication();
        return this.s3Service.downloadProfilePhoto(key);
    }

    private User getMeUserAuthentication(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public UserResponseDTO create(String email, String password, UserRole role) {
        if (this.userRepository.findByEmail(email).isPresent()) {
            throw new UserExistsException();
        }

        String encryptedPassword = this.cryptPasswordEncoder.encode(password);
        User newUser = new User(
                email,
                encryptedPassword,
                role
        );

        if (defaultEmailVerified) {
            newUser.setEmailVerified(true);
        }

        this.userRepository.save(newUser);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail(newUser.getEmail());
        userResponseDTO.setId(newUser.getId());
        userResponseDTO.setRole(newUser.getRole());
        return userResponseDTO;
    }

    public UserResponseDTO getById(UUID id) {
        User user = this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return buildUserResponseDTO(user);
    }

    public UserResponseDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return buildUserResponseDTO(user);
    }

    @Override
    public User findEntityById(UUID id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserResponseDTO deleteById(UUID id, String password) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        boolean passwordMatches =
                cryptPasswordEncoder.matches(password, user.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        userRepository.delete(user);

        return  buildUserResponseDTO(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ValidateEmailResponseDTO validateEmail(ValidateEmailRequestDTO request)
            throws RequestNotFoundException,
            RequestCodeExpiredException,
            InvalidCodeException {

        UUID verificationId = request.getVerificationId();
        String code = request.getCode();

        UserVerificationCode verification =
                this.userVerificationRepository.findById(verificationId)
                        .orElseThrow(RequestNotFoundException::new);

        if (!verification.getCode().equals(code)) {
            throw new InvalidCodeException();
        }

        if (!Utils.isCodeValid(verification.getGeneratedAt())) {
            throw new RequestCodeExpiredException();
        }

        User user = verification.getUser();
        user.setEmailVerified(true);
        this.userRepository.save(user);

        verification.setVerified(true);
        this.userVerificationRepository.save(verification);

        return ValidateEmailResponseDTO.builder()
                .email(user.getEmail())
                .verified(true)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RequestValidationEmailResponseDTO sendEmailVerification(
            RequestValidationEmailDTO request) throws UserNotFoundException {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        UUID id = this.sendEmail(user, VerificationType.EMAIL);
        return RequestValidationEmailResponseDTO.builder()
                .verificationId(id)
                .build();
    }

    @Override
    @Transactional
    public void requestPasswordReset(RequestPasswordResetDTO request) {
        String email = request.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        this.sendEmail(user, VerificationType.PASSWORD_RESET);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public StatusPasswordResetDTO resetPassword(ResetPasswordDTO request  ) {
        String email = request.getEmail();
        String code = request.getCode();
        String newPassword = request.getNewPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        List<UserVerificationCode> verifications =
                userVerificationRepository.findByUserAndTypeAndVerifiedFalse(
                        user,
                        VerificationType.PASSWORD_RESET
                );

        if (verifications.isEmpty()) {
            throw new RequestNotFoundException();
        }

        UserVerificationCode verification = verifications
                .stream()
                .max(Comparator.comparing(UserVerificationCode::getGeneratedAt))
                .orElseThrow(RequestNotFoundException::new);

        if (!verification.getCode().equals(code)) {
            throw new InvalidCodeException();
        }

        if (!Utils.isCodeValid(verification.getGeneratedAt())) {
            throw new RequestCodeExpiredException();
        }

        user.setPassword(cryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);

        verification.setVerified(true);
        userVerificationRepository.save(verification);

        return StatusPasswordResetDTO.builder().email(email).swapPassword(true).build();
    }

    @Override
    public UploadProfileImageResponse updateProfileImage(MultipartFile file) {
        User user = getMeUserAuthentication();

        try {
            byte[] bytes = file.getBytes();
            String profileKey = this.s3Service.uploadProfilePhoto(user.getId(), bytes);

            user.setProfileKey(profileKey);
            userRepository.save(user);

            return new UploadProfileImageResponse(user.getId(),profileKey);

        } catch (IOException e) {
            throw new RuntimeException("ERROR PROFILE READ", e);
        }
    }


    private UUID sendEmail(User user, VerificationType type) {
        try {
            String code = Utils.generateVerificationCode();

            UserVerificationCode verification = UserVerificationCode.builder()
                    .user(user)
                    .code(code)
                    .type(VerificationType.EMAIL)
                    .generatedAt(LocalDateTime.now())
                    .verified(false)
                    .build();
            switch (type){
                case VerificationType.EMAIL -> this.emailService.sendValidationEmail(user.getEmail(),code);
                case VerificationType.PASSWORD_RESET -> this.emailService.sendValidationEmailForResetPassword(user.getEmail(), code);
                default -> throw new IllegalStateException("Unexpected value: " + type);
            }
            this.userVerificationRepository.save(verification);
            return verification.getId();
        } catch (Exception e) {
            throw new ErroSendEmailException();
        }
    }

    private UserResponseDTO buildUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .profileKey(user.getProfileKey())
                .role(user.getRole())
                .build();
    }
}
