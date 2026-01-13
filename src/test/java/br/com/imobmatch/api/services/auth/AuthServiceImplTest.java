package br.com.imobmatch.api.services.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.imobmatch.api.dtos.auth.AuthenticationDTO;
import br.com.imobmatch.api.dtos.auth.LoginResponseDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.auth.TokenInvalidException;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.user.enums.UserRole;
import br.com.imobmatch.api.services.user.UserService;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthServiceImplTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    private UUID userId;

    @BeforeEach
    void setup() {
        UserResponseDTO userDTO = userService.create(
                "test@email.com",
                "123456",
                UserRole.OWNER
        );
        userId = userDTO.getId();
    }

    @Test
    void shouldLoginSuccessfully() {
        AuthenticationDTO dto = new AuthenticationDTO(
                "test@email.com",
                "123456"
        );

        LoginResponseDTO response = authService.login(dto);

        assertNotNull(response);
        assertNotNull(response.getToken());
    }

    @Test
    void shouldRefreshTokenSuccessfully() {
        AuthenticationDTO dto = new AuthenticationDTO(
                "test@email.com",
                "123456"
        );

        LoginResponseDTO login = authService.login(dto);

        LoginResponseDTO refreshed =
                authService.refreshToken(login.getToken());

        assertNotNull(refreshed);
        assertNotNull(refreshed.getToken());
        assertEquals(login.getToken(), refreshed.getToken());
    }

    @Test
    void shouldThrowTokenInvalidWhenUserDoesNotExist() {
        AuthenticationDTO dto = new AuthenticationDTO(
                "test@email.com",
                "123456"
        );

        LoginResponseDTO login = authService.login(dto);

        User userEntity = userService.findEntityById(userId);
        userService.deleteById(userEntity.getId(), "123456");

        assertThrows(
                TokenInvalidException.class,
                () -> authService.refreshToken(login.getToken())
        );
    }
}
