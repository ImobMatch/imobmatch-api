package br.com.imobmatch.api.services.auth;

import static org.junit.jupiter.api.Assertions.*;

import br.com.imobmatch.api.dtos.auth.AuthenticationDTO;
import br.com.imobmatch.api.dtos.auth.LoginResponseDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.auth.TokenInvalidException;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.enums.UserRole;
import br.com.imobmatch.api.services.user.UserService;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


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

        User userEntity = userService.findEntityById(userDTO.getId());
        userEntity.setEmailVerified(true);

        userId = userDTO.getId();
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldLoginSuccessfully() {
        AuthenticationDTO dto =
                new AuthenticationDTO("test@email.com", "123456");

        LoginResponseDTO response = authService.login(dto);

        assertNotNull(response);
        assertNotNull(response.getAccessToken());
    }

    @Test
    void shouldRefreshTokenSuccessfully() {
        AuthenticationDTO dto =
                new AuthenticationDTO("test@email.com", "123456");

        LoginResponseDTO login = authService.login(dto);

        LoginResponseDTO refreshed =
                authService.refreshToken(login.getRefreshToken());

        assertNotNull(refreshed);
        assertNotNull(refreshed.getAccessToken());
        assertNotEquals(login.getAccessToken(), refreshed.getAccessToken());
    }

    @Test
    void shouldRefreshTokenFailure() {
        AuthenticationDTO dto =
                new AuthenticationDTO("test@email.com", "123456");

        LoginResponseDTO login = authService.login(dto);

        assertThrows(TokenInvalidException.class, () -> authService.refreshToken(login.getAccessToken()));
    }

    @Test
    void shouldThrowTokenInvalidWhenUserDoesNotExist() {
        AuthenticationDTO dto =
                new AuthenticationDTO("test@email.com", "123456");

        LoginResponseDTO login = authService.login(dto);

        User userEntity = userService.findEntityById(userId);
        userService.deleteById(userEntity.getId(), "123456");

        assertThrows(
                TokenInvalidException.class,
                () -> authService.refreshToken(login.getAccessToken())
        );
    }

    @Test
    void shouldReturnLoggedUser() {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        "test@email.com",
                        null,
                        List.of()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserResponseDTO me = authService.getMe();

        assertEquals("test@email.com", me.getEmail());
    }


    @Test
    void securityContextShouldBeCleanBetweenTests() {
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
