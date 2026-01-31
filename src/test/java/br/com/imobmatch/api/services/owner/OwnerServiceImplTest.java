package br.com.imobmatch.api.services.owner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.owner.OwnerUpdateDTO;
import br.com.imobmatch.api.dtos.owner.OwnerCreateDTO;
import br.com.imobmatch.api.dtos.owner.OwnerResponseDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.models.owner.Owner;
import br.com.imobmatch.api.repositories.OwnerRepository;
import br.com.imobmatch.api.repositories.UserRepository;
import br.com.imobmatch.api.services.auth.AuthService;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test the ownerService
 * Ignore authentication and security rules
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class OwnerServiceImplTest {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private UserRepository userRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private AuthService authService;

    @BeforeEach
    void setup() {

        // Generated security hash
        when(passwordEncoder.encode(any())).thenReturn("$2a$10$FakeHashForTestingPurposesOnly");
        // Allow returns true for all entered passwords.
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
    }

    @Test
    @DisplayName("Create an owner and user")
    void testCreateOwner_Success() {

        OwnerCreateDTO dto = createValidOwnerDTO("newowner@test.com", "11122233344");
        OwnerResponseDTO response = ownerService.createOwner(dto);
        assertNotNull(response.getId());
        assertEquals(dto.getName(), response.getName());

        boolean userExists = userRepository.existsById(response.getId());
        boolean ownerExists = ownerRepository.existsById(response.getId());

        assertTrue(userExists, "User not created");
        assertTrue(ownerExists, "The owner must be created in the owners table");
    }

    @Test
    @DisplayName("Update Owner")
    void testUpdateOwner_Success() {

        OwnerResponseDTO created = ownerService.createOwner(createValidOwnerDTO("update@test.com", "99988877766"));
        mockAuthenticatedUser(created.getId());

        OwnerUpdateDTO patchDto = new OwnerUpdateDTO();
        patchDto.setName("Updated Name");

        OwnerResponseDTO updated = ownerService.updateOwner(patchDto);
        assertEquals("Updated Name", updated.getName());

        Owner ownerInDb = ownerRepository.findById(created.getId()).orElseThrow();
        assertEquals("Updated Name", ownerInDb.getName());
    }

    @Test
    @DisplayName("Delete Owner")
    void testDeleteOwner_Success() {
        OwnerResponseDTO created = ownerService.createOwner(createValidOwnerDTO("delete@test.com", "00000000000"));
        UUID id = created.getId();
        mockAuthenticatedUser(id);

        PasswordUserDeleteDTO deleteDto = new PasswordUserDeleteDTO("123456");
        ownerService.deleteOwner(deleteDto);

        assertTrue(ownerRepository.findById(id).isEmpty(), "Owner must be removed");
        assertTrue(userRepository.findById(id).isEmpty(), "User must be removed");
    }

    private void mockAuthenticatedUser(UUID userId) {
        UserResponseDTO mockUser = new UserResponseDTO();
        mockUser.setId(userId);
        when(authService.getMe()).thenReturn(mockUser);
    }

    private OwnerCreateDTO createValidOwnerDTO(String email, String cpf) {
        LocalDate date = LocalDate.of(2005, 3, 4);
        OwnerCreateDTO dto = new OwnerCreateDTO();
        dto.setName("Test Silva");
        dto.setEmail(email);
        dto.setCpf(cpf);
        dto.setPassword("123456");
        dto.setPersonalPhoneNumber("123456789");
        dto.setWhatsAppPhoneNumber("987456321");
        dto.setBirthDate(date);
        return dto;
    }
}
