package br.com.imobmatch.api.services.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import br.com.imobmatch.api.dtos.email.RequestValidationEmailDTO;
import br.com.imobmatch.api.dtos.email.RequestValidationEmailResponseDTO;
import br.com.imobmatch.api.dtos.email.ValidateEmailRequestDTO;
import br.com.imobmatch.api.dtos.email.ValidateEmailResponseDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.email.InvalidCodeException;
import br.com.imobmatch.api.exceptions.user.UserExistsException;
import br.com.imobmatch.api.exceptions.user.UserNotFoundException;
import br.com.imobmatch.api.infra.email.services.EmailService;
import br.com.imobmatch.api.models.user.UserVerificationCode;
import br.com.imobmatch.api.models.enums.UserRole;
import br.com.imobmatch.api.repositories.UserVerificationCodeRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserVerificationCodeRepository userVerificationRepository;

    @MockBean
    private EmailService emailService;

    private UserResponseDTO user;

    @BeforeEach
    void setup() throws UserExistsException {
        this.user = userService.create(
                "test@example.com",
                "12345678",
                UserRole.OWNER
        );
    }

    @Test
    void testCreateUser_Success() throws UserExistsException {
        UserResponseDTO response =
                userService.create("owner@example.com", "12345678", UserRole.OWNER);

        assertNotNull(response.getId());
        assertEquals("owner@example.com", response.getEmail());
        assertEquals(UserRole.OWNER, response.getRole());
    }

    @Test
    void testCreateUser_UserExists() throws UserExistsException {
        userService.create("exists@example.com", "12345678", UserRole.OWNER);

        assertThrows(UserExistsException.class,
                () -> userService.create("exists@example.com", "12345678", UserRole.OWNER));
    }

    @Test
    void testGetById_UserExists() throws Exception {
        UserResponseDTO created =
                userService.create("owner2@example.com", "12345678", UserRole.OWNER);

        UserResponseDTO response = userService.getById(created.getId());

        assertEquals(created.getId(), response.getId());
        assertEquals("owner2@example.com", response.getEmail());
    }

    @Test
    void testGetById_UserNotFound() {
        UUID randomId = UUID.randomUUID();
        assertThrows(UserNotFoundException.class,
                () -> userService.getById(randomId));
    }

    @Test
    void testGetByEmail_UserExists() throws Exception {
        userService.create("owner3@example.com", "12345678", UserRole.OWNER);

        UserResponseDTO response = userService.getByEmail("owner3@example.com");

        assertEquals("owner3@example.com", response.getEmail());
        assertEquals(UserRole.OWNER, response.getRole());
    }

    @Test
    void testGetByEmail_UserNotFound() {
        assertThrows(UserNotFoundException.class,
                () -> userService.getByEmail("notfound@example.com"));
    }

    @Test
    void testSendEmailVerificationCode_Success() {
        RequestValidationEmailResponseDTO response =
                userService.sendEmailVerificationCodeForEmail(
                        RequestValidationEmailDTO.builder()
                                .email(user.getEmail())
                                .build()
                );

        assertNotNull(response.getVerificationId());

        verify(emailService).sendEmail(
                eq(user.getEmail()),
                anyString(),
                contains("verification code")
        );
    }

    @Test
    void testValidateEmail_Success() {
        UUID verificationId =
                userService.sendEmailVerificationCodeForEmail(
                        RequestValidationEmailDTO.builder()
                                .email(user.getEmail())
                                .build()
                ).getVerificationId();

        UserVerificationCode verification =
                userVerificationRepository.findById(verificationId)
                        .orElseThrow();

        ValidateEmailResponseDTO response =
                userService.validateEmail(
                        new ValidateEmailRequestDTO(
                                verificationId,
                                verification.getCode()
                        )
                );

        assertEquals(user.getEmail(), response.getEmail());
        assertTrue(response.isVerified());
    }

    @Test
    void testValidateEmail_InvalidCode() {
        UUID verificationId =
                userService.sendEmailVerificationCodeForEmail(
                        RequestValidationEmailDTO.builder()
                                .email(user.getEmail())
                                .build()
                ).getVerificationId();

        assertThrows(InvalidCodeException.class, () ->
                userService.validateEmail(
                        new ValidateEmailRequestDTO(verificationId, "000000")
                )
        );
    }
}
