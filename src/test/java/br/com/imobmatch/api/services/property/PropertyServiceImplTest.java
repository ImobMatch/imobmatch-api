package br.com.imobmatch.api.services.property;

import br.com.imobmatch.api.dtos.property.PropertyCreateDTO;
import br.com.imobmatch.api.dtos.property.PropertyFilterDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.mappers.PropertyMapper;
import br.com.imobmatch.api.models.enums.PropertyType;
import br.com.imobmatch.api.models.property.Property;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.repositories.PropertyRepository;
import br.com.imobmatch.api.services.auth.AuthService;
import br.com.imobmatch.api.services.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceImplTest {

    @InjectMocks
    private PropertyServiceImpl propertyService;

    @Mock
    private PropertyRepository repository;

    @Mock
    private PropertyMapper mapper;

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    @Test
    @DisplayName("Should create property successfully when all data is valid")
    void createProperty_Success() {
        UUID userId = UUID.randomUUID();
        UUID propertyId = UUID.randomUUID();

        PropertyCreateDTO createDTO = PropertyCreateDTO.builder()
                .ownerCpf("12345678900")
                .isAvailable(true)
                .build();

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userId);

        User userEntity = new User();
        userEntity.setId(userId);

        Property propertyEntity = new Property();

        Property savedProperty = new Property();
        savedProperty.setId(propertyId);
        savedProperty.setPublisher(userEntity);

        PropertyResponseDTO responseDTO = new PropertyResponseDTO();
        responseDTO.setId(propertyId);

        when(mapper.toEntity(createDTO)).thenReturn(propertyEntity);
        when(authService.getMe()).thenReturn(userResponseDTO);
        when(userService.findEntityById(userId)).thenReturn(userEntity);
        when(repository.save(propertyEntity)).thenReturn(savedProperty);
        when(mapper.toDTO(savedProperty)).thenReturn(responseDTO);

        PropertyResponseDTO result = propertyService.createProperty(createDTO);

        assertNotNull(result);
        assertEquals(propertyId, result.getId());
        assertEquals(userEntity, propertyEntity.getPublisher());

        verify(mapper).toEntity(createDTO);
        verify(authService).getMe();
        verify(userService).findEntityById(userId);
        verify(repository).save(propertyEntity);
        verify(mapper).toDTO(savedProperty);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when publisher user is not found")
    void createProperty_UserNotFound() {
        PropertyCreateDTO createDTO = new PropertyCreateDTO();
        Property propertyEntity = new Property();

        UUID userId = UUID.randomUUID();
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userId);

        when(mapper.toEntity(createDTO)).thenReturn(propertyEntity);
        when(authService.getMe()).thenReturn(userResponseDTO);
        when(userService.findEntityById(userId)).thenThrow(new EntityNotFoundException("User not found"));

        assertThrows(EntityNotFoundException.class, () -> propertyService.createProperty(createDTO));

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should propagate exception when repository fails to save")
    void createProperty_RepositoryFailure() {
        PropertyCreateDTO createDTO = new PropertyCreateDTO();
        UUID userId = UUID.randomUUID();

        Property propertyEntity = new Property();
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userId);
        User userEntity = new User();
        userEntity.setId(userId);

        when(mapper.toEntity(createDTO)).thenReturn(propertyEntity);
        when(authService.getMe()).thenReturn(userResponseDTO);
        when(userService.findEntityById(userId)).thenReturn(userEntity);
        when(repository.save(propertyEntity)).thenThrow(new DataIntegrityViolationException("Database error"));

        assertThrows(DataIntegrityViolationException.class, () -> propertyService.createProperty(createDTO));

        verify(repository).save(propertyEntity);
    }

    @Test
    @DisplayName("Should return filtered properties when complex filter is provided")
    void findAll_WithComplexFilters_Success() {
        PropertyFilterDTO complexFilter = PropertyFilterDTO.builder()
                .type(PropertyType.APARTMENT)
                .isAvailable(true)
                .minArea(new BigDecimal("50.0"))
                .maxArea(new BigDecimal("200.0"))
                .minBedrooms((short) 2)
                .maxCondoPrice(new BigDecimal("1500.00"))
                .build();

        Property propertyMatch = new Property();
        propertyMatch.setId(UUID.randomUUID());
        propertyMatch.setType(PropertyType.APARTMENT);

        List<Property> mockedEntityList = List.of(propertyMatch);

        PropertyResponseDTO responseDTO = new PropertyResponseDTO();
        responseDTO.setId(propertyMatch.getId());

        when(repository.findAll(any(Specification.class))).thenReturn(mockedEntityList);
        when(mapper.toDTO(propertyMatch)).thenReturn(responseDTO);

        List<PropertyResponseDTO> result = propertyService.findAll(complexFilter);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(propertyMatch.getId(), result.get(0).getId());

        verify(repository).findAll(any(Specification.class));
        verify(mapper, times(1)).toDTO(propertyMatch);
    }

    @Test
    @DisplayName("Should return empty list when no property matches the filters")
    void findAll_WithFilters_NoMatch() {
        PropertyFilterDTO restrictiveFilter = PropertyFilterDTO.builder()
                .minArea(new BigDecimal("9000.0"))
                .build();

        when(repository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        List<PropertyResponseDTO> result = propertyService.findAll(restrictiveFilter);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository).findAll(any(Specification.class));
        verify(mapper, never()).toDTO(any());
    }

    @Test
    @DisplayName("Should return all properties when filter is empty")
    void findAll_NoFilters_ReturnsAll() {
        PropertyFilterDTO emptyFilter = new PropertyFilterDTO();

        Property p1 = new Property(); p1.setId(UUID.randomUUID());
        Property p2 = new Property(); p2.setId(UUID.randomUUID());
        List<Property> allProperties = List.of(p1, p2);

        when(repository.findAll(any(Specification.class))).thenReturn(allProperties);
        when(mapper.toDTO(any(Property.class))).thenReturn(new PropertyResponseDTO());

        List<PropertyResponseDTO> result = propertyService.findAll(emptyFilter);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(mapper, times(2)).toDTO(any(Property.class));
    }
}