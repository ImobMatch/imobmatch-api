package br.com.imobmatch.api.services.broker;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import br.com.imobmatch.api.dtos.broker.BrokerPatchDTO;
import br.com.imobmatch.api.dtos.broker.BrokerPostDTO;
import br.com.imobmatch.api.dtos.broker.BrokerResponseDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.infra.security.services.token.TokenService;
import br.com.imobmatch.api.repositories.BrokerRepository;
import br.com.imobmatch.api.repositories.UserRepository;
import br.com.imobmatch.api.services.auth.AuthService;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@Transactional
class BrokerServiceImplTest {

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BrokerRepository brokerRepository;
    
    @Autowired
    private BrokerService brokerService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {

        // Generated security hash
        when(passwordEncoder.encode(any())).thenReturn("$2a$10$FakeHashForTestingPurposesOnly");
        // Allow returns true for all entered passwords.
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
    }

    @Test
    @DisplayName("Create a broker and user")
    void testCreateBroker() {
        BrokerPostDTO brokerPostDTO = new BrokerPostDTO();
        brokerPostDTO.setName("Test Broker");
        brokerPostDTO.setEmail("test@example.com");
        brokerPostDTO.setPassword("password123");
        brokerPostDTO.setCreci("12345J");
        brokerPostDTO.setCpf("11122233344");
        brokerPostDTO.setBirthDate(java.time.LocalDate.of(1990, 1, 1));
        brokerPostDTO.setWhatsAppPhoneNumber("1234567890");

        BrokerResponseDTO brokerResponseDTO = brokerService.createBroker(brokerPostDTO);
        assertNotNull(brokerResponseDTO);
        assertEquals("Test Broker", brokerResponseDTO.getName());

        boolean userExists = userRepository.existsById(brokerResponseDTO.getId());
        assertTrue(userExists);

        boolean brokerExists = brokerRepository.existsById(brokerResponseDTO.getId());
        assertTrue(brokerExists);
    }

    @Test
    @DisplayName("Update broker successfully")
    void testUpdateBroker() {
        BrokerPostDTO brokerPostDTO = new BrokerPostDTO();
        brokerPostDTO.setName("Test Broker");
        brokerPostDTO.setEmail("test@example.com");
        brokerPostDTO.setPassword("password123");
        brokerPostDTO.setCreci("12345J");
        brokerPostDTO.setCpf("11122233344");
        brokerPostDTO.setBirthDate(java.time.LocalDate.of(1990, 1, 1));
        brokerPostDTO.setWhatsAppPhoneNumber("1234567890");

        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        mockAuthenticatedUser(createdBroker.getId());

        // Update the broker
        BrokerPatchDTO patchDto = new BrokerPatchDTO();
        patchDto.setName("Updated Test Broker");

        BrokerResponseDTO updatedBroker = brokerService.updateBroker(patchDto);
        assertNotNull(updatedBroker);
        assertEquals("Updated Test Broker", updatedBroker.getName());
    }

    @Test
    @DisplayName("Delete broker successfully")
    void testDeleteBroker() {
        BrokerPostDTO brokerPostDTO = new BrokerPostDTO();
        brokerPostDTO.setName("Test Broker");
        brokerPostDTO.setEmail("test@example.com");
        brokerPostDTO.setPassword("password123");
        brokerPostDTO.setCreci("12345J");
        brokerPostDTO.setCpf("11122233344");
        brokerPostDTO.setBirthDate(java.time.LocalDate.of(1990, 1, 1));
        brokerPostDTO.setWhatsAppPhoneNumber("1234567890");

        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        mockAuthenticatedUser(createdBroker.getId());

        PasswordUserDeleteDTO deleteDto = new PasswordUserDeleteDTO("password123");
        brokerService.deleteBroker(deleteDto);

        assertNull(brokerRepository.findById(createdBroker.getId()).orElse(null));
    }

    private void mockAuthenticatedUser(UUID userId) {
        UserResponseDTO mockUser = new UserResponseDTO();
        mockUser.setId(userId);
        when(authService.getMe()).thenReturn(mockUser);
    }
    
}