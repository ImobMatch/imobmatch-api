package br.com.imobmatch.api.services.user;

import br.com.imobmatch.api.dtos.email.RequestValidationEmailResponseDTO;
import br.com.imobmatch.api.dtos.email.RequestValidationEmailDTO;
import br.com.imobmatch.api.dtos.email.ValidateEmailRequestDTO;
import br.com.imobmatch.api.dtos.email.ValidateEmailResponseDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.auth.AuthenticationException;
import br.com.imobmatch.api.exceptions.email.ErroSendEmailException;
import br.com.imobmatch.api.exceptions.email.InvalidCodeException;
import br.com.imobmatch.api.exceptions.email.RequestCodeExpiredException;
import br.com.imobmatch.api.exceptions.email.RequestNotFoundException;
import br.com.imobmatch.api.exceptions.user.UserExistsException;
import br.com.imobmatch.api.exceptions.user.UserNotFoundException;
import br.com.imobmatch.api.infra.email.services.EmailService;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.user.UserVerificationCode;
import br.com.imobmatch.api.models.user.enums.UserRole;
import br.com.imobmatch.api.models.user.enums.VerificationType;
import br.com.imobmatch.api.repositories.UserRepository;
import br.com.imobmatch.api.utils.Utils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import br.com.imobmatch.api.repositories.UserVerificationCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder cryptPasswordEncoder;
    private final UserVerificationCodeRepository userVerificationRepository;
    private final EmailService emailService;


    public UserResponseDTO create(String email, String password, UserRole role) {
        if(this.userRepository.findByEmail(email).isPresent()){
            throw new UserExistsException();
        }

        String encryptedPassword = this.cryptPasswordEncoder.encode(password);
        User newUser = new User(
            email,
            encryptedPassword,
            role
        );

        this.userRepository.save(newUser);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail(newUser.getEmail());
        userResponseDTO.setId(newUser.getId());
        userResponseDTO.setRole(newUser.getRole());
        return userResponseDTO;
    }

    public UserResponseDTO getById(UUID id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if(optionalUser.isPresent()){
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setId(optionalUser.get().getId());
            userResponseDTO.setEmail(optionalUser.get().getEmail());
            userResponseDTO.setRole(optionalUser.get().getRole());
            return userResponseDTO;
        }
        throw new UserNotFoundException();
    }

    public UserResponseDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Override
    public User findEntityById(UUID id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return user;
    }

    @Override
    public UserResponseDTO deleteById(UUID id, String password) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        boolean passwordMatches = cryptPasswordEncoder.matches(password, user.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        userRepository.delete(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setRole(user.getRole());

        return userResponseDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ValidateEmailResponseDTO validateEmail(ValidateEmailRequestDTO request) throws RequestNotFoundException,
            RequestCodeExpiredException, InvalidCodeException {
        UUID verificationId = request.getVerificationId();
        String code = request.getCode();

        UserVerificationCode verification = this.userVerificationRepository.findById(verificationId)
                .orElseThrow(RequestNotFoundException::new);

        if (!verification.getCode().equals(code)){
            throw new InvalidCodeException();
        }

        if(!Utils.isCodeValid(verification.getGeneratedAt())){
            throw  new RequestCodeExpiredException();
        }

        User user = verification.getUser();
        user.setEmailVerified(true);
        this.userRepository.save(user);
        verification.setVerified(true);

        return ValidateEmailResponseDTO.builder()
                .email(user.getEmail())
                .verified(true)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RequestValidationEmailResponseDTO sendEmailVerificationCodeForEmail(RequestValidationEmailDTO request) throws UserNotFoundException {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        UUID id = this.sendEmail(user);
        return RequestValidationEmailResponseDTO.builder()
                .id(id)
                .build();
    }

    protected UUID sendEmail(User user){
        try {
            String code = Utils.generateVerificationCode();
            UserVerificationCode verification = UserVerificationCode.builder()
                    .user(user)
                    .code(code)
                    .type(VerificationType.EMAIL)
                    .generatedAt(LocalDateTime.now())
                    .verified(false)
                    .build();
            this.emailService.sendEmail(
                    user.getEmail(),
                    "Email verification for ImobMatch",
                    "Hello\nYour verification code is: " + code
            );
            this.userVerificationRepository.save(verification);
            return verification.getId();
        } catch (Exception e) {
            throw new ErroSendEmailException();
        }
    }
}
