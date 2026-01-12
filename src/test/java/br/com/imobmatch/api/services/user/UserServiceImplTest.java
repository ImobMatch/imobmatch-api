package br.com.imobmatch.api.services.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.user.UserExistsException;
import br.com.imobmatch.api.exceptions.user.UserNotFoundException;
import br.com.imobmatch.api.models.user.UserRole;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @BeforeEach
    void setup() {
        // if do you clean DB
    }

    @Test
    void testCreateUser_Success() throws UserExistsException {
        String email = "owner@example.com";
        String password = "123456";
        UserRole role = UserRole.OWNER;

        UserResponseDTO response = userService.create(email, password, role);

        assertNotNull(response.getId());
        assertEquals(email, response.getEmail());
        assertEquals(role, response.getRole());
    }

    @Test
    void testCreateUser_UserExists() throws UserExistsException {
        String email = "owner@example.com";
        userService.create(email, "123456", UserRole.OWNER);

        assertThrows(UserExistsException.class, () -> userService.create(email, "123456", UserRole.OWNER));
    }

    @Test
    void testGetById_UserExists() throws Exception {
        UserResponseDTO created = userService.create("owner2@example.com", "123456", UserRole.OWNER);

        UserResponseDTO response = userService.getById(created.getId());

        assertEquals(created.getId(), response.getId());
        assertEquals("owner2@example.com", response.getEmail());
    }

    @Test
    void testGetById_UserNotFound() {
        UUID randomId = UUID.randomUUID();
        assertThrows(UserNotFoundException.class, () -> userService.getById(randomId));
    }

    @Test
    void testGetByEmail_UserExists() throws Exception {
        userService.create("owner3@example.com", "123456", UserRole.OWNER);

        UserResponseDTO response = userService.getByEmail("owner3@example.com");

        assertEquals("owner3@example.com", response.getEmail());
        assertEquals(UserRole.OWNER, response.getRole());
    }

    @Test
    void testGetByEmail_UserNotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.getByEmail("notfound@example.com"));
    }
}
