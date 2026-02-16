package br.com.imobmatch.api.services.broker;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.UUID;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;

import org.junit.jupiter.api.AfterEach;
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
import br.com.imobmatch.api.models.enums.BrokerAccountStatus;
import br.com.imobmatch.api.models.enums.PropertyBusinessType;
import br.com.imobmatch.api.models.enums.PropertyType;
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

    private BrokerPostDTO brokerPostDTO;

    @BeforeEach
    void setup() {

        // Generated security hash
        when(passwordEncoder.encode(any())).thenReturn("$2a$10$FakeHashForTestingPurposesOnly");
        // Allow returns true for all entered passwords.
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        brokerPostDTO = new BrokerPostDTO();
        brokerPostDTO.setName("Test Broker");
        brokerPostDTO.setEmail("test@example.com");
        brokerPostDTO.setPassword("password123");
        brokerPostDTO.setCreci("12345J");
        brokerPostDTO.setCpf("11122233344");
        brokerPostDTO.setBirthDate(java.time.LocalDate.of(1990, 1, 1));
        brokerPostDTO.setWhatsAppPhoneNumber("1234567890");
    }

    @AfterEach
    void tearDown() {
        brokerRepository.deleteAll();
        userRepository.deleteAll();
    }

    // Sucessful Cases

    @Test
    @DisplayName("Create a broker and user")
    void testCreateBroker() {
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
    @DisplayName("Admin updates broker account data successfully")
    void testAdminUpdateBrokerAccountData() {
        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        mockAuthenticatedUser(createdBroker.getId());

        // Update the broker
        BrokerPatchDTO patchDto = new BrokerPatchDTO();
        patchDto.setName("Updated Test Broker");

        BrokerResponseDTO updatedBroker = brokerService.updateBroker(createdBroker.getId(), patchDto);
        assertNotNull(updatedBroker);
        assertEquals("Updated Test Broker", updatedBroker.getName());
    }

    @Test
    @DisplayName("Get broker by ID successfully")
    void testGetBrokerById() {
        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        BrokerResponseDTO fetchedBroker = brokerService.getBrokerById(createdBroker.getId());
        assertNotNull(fetchedBroker);
        assertEquals(createdBroker.getId(), fetchedBroker.getId());
    }

    @Test
    @DisplayName("Get my broker data successfully")
    void testGetMeBroker() {
        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        mockAuthenticatedUser(createdBroker.getId());

        BrokerResponseDTO fetchedBroker = brokerService.getBroker();
        assertNotNull(fetchedBroker);
        assertEquals(createdBroker.getId(), fetchedBroker.getId());
    }

    @Test
    @DisplayName("Get broker by email successfully")
    void testGetBrokerByEmail() {
        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        BrokerResponseDTO fetchedBroker = brokerService.getBrokerByEmail(createdBroker.getEmail());
        assertNotNull(fetchedBroker);
        assertEquals(createdBroker.getId(), fetchedBroker.getId());
    }

    @Test
    @DisplayName("Get broker by CRECI successfully")
    void testGetBrokerByCreci() {
        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        BrokerResponseDTO fetchedBroker = brokerService.getBrokerByCreci(createdBroker.getCreci());
        assertNotNull(fetchedBroker);
        assertEquals(createdBroker.getId(), fetchedBroker.getId());
    }

    @Test
    @DisplayName("Get broker by CPF successfully")
    void testGetBrokerByCpf() {
        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        BrokerResponseDTO fetchedBroker = brokerService.getBrokerByCpf(createdBroker.getCpf());
        assertNotNull(fetchedBroker);
        assertEquals(createdBroker.getId(), fetchedBroker.getId());
    }

    @Test
    @DisplayName("List brokers by name successfully")
    void testListBrokersByName() {
        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        var brokers = brokerService.getBrokersByName("Test Broker");
        assertFalse(brokers.isEmpty());
        assertEquals(1, brokers.size());
    }

    @Test
    @DisplayName("List brokers by region interest successfully")
    void testListBrokersByRegionInterest() {
        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        BrokerPatchDTO patchDto = new BrokerPatchDTO();
        patchDto.setRegionsInterest(Set.of("PB"));

        mockAuthenticatedUser(createdBroker.getId());
        brokerService.updateBroker(patchDto);

        var brokers = brokerService.getBrokersByRegionInterest("PB");
        assertFalse(brokers.isEmpty());
        assertEquals(1, brokers.size());
    }

    @Test
    @DisplayName("List brokers by property type successfully")
    void testListBrokersByPropertyType() {
        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        BrokerPatchDTO patchDto = new BrokerPatchDTO();
        patchDto.setPropertyTypes(Set.of(PropertyType.APARTMENT));

        mockAuthenticatedUser(createdBroker.getId());
        brokerService.updateBroker(patchDto);

        var brokers = brokerService.getBrokersByPropertyType(PropertyType.APARTMENT);
        assertFalse(brokers.isEmpty());
        assertEquals(1, brokers.size());
    }

    @Test
    @DisplayName("List brokers by business type successfully")
    void testListBrokersByBusinessType() {
        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        BrokerPatchDTO patchDto = new BrokerPatchDTO();
        patchDto.setBusinessTypes(Set.of(PropertyBusinessType.SALE));

        mockAuthenticatedUser(createdBroker.getId());
        brokerService.updateBroker(patchDto);

        var brokers = brokerService.getBrokersByBusinessType(PropertyBusinessType.SALE);
        assertFalse(brokers.isEmpty());
        assertEquals(1, brokers.size());
    }

    @Test
    @DisplayName("List all brokers successfully")
    void testListAllBrokers() {
        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        var brokers = brokerService.getAllBrokers();
        assertFalse(brokers.isEmpty());
        assertEquals(1, brokers.size());
    }

    @Test
    @DisplayName("List brokers by account status successfully")
    void testListBrokersByAccountStatus() {
        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        var brokers = brokerService.getBrokersByAccountStatus(BrokerAccountStatus.PENDING);
        assertFalse(brokers.isEmpty());
        assertEquals(1, brokers.size());
    }

    @Test
    @DisplayName("Delete broker successfully")
    void testDeleteBroker() {
        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        mockAuthenticatedUser(createdBroker.getId());

        PasswordUserDeleteDTO deleteDto = new PasswordUserDeleteDTO("password123");
        brokerService.deleteBroker(deleteDto);

        assertNull(brokerRepository.findById(createdBroker.getId()).orElse(null));
    }

    @Test
    @DisplayName("Delete broker by admin successfully")
    void testDeleteBrokerByAdmin() {
        BrokerResponseDTO createdBroker = brokerService.createBroker(brokerPostDTO);
        assertNotNull(createdBroker);

        brokerService.deleteBroker(createdBroker.getId());

        assertNull(brokerRepository.findById(createdBroker.getId()).orElse(null));
    }

    private void mockAuthenticatedUser(UUID userId) {
        UserResponseDTO mockUser = new UserResponseDTO();
        mockUser.setId(userId);
        when(authService.getMe()).thenReturn(mockUser);
    }
    
}