package br.com.imobmatch.api.services.property;

import br.com.imobmatch.api.dtos.property.PropertyCreateDTO;
import br.com.imobmatch.api.dtos.property.PropertyFilterDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.dtos.property.PropertyUpdateDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.property.PropertyNotFoundException;
import br.com.imobmatch.api.mappers.PropertyMapper;
import br.com.imobmatch.api.models.property.Property;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.repositories.PropertyRepository;
import br.com.imobmatch.api.services.auth.AuthService;
import br.com.imobmatch.api.services.user.UserService;
import br.com.imobmatch.api.specs.property.PropertySpecs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository repository;
    private final PropertyMapper mapper;
    private final UserService userService;
    private final AuthService authService;

    @Transactional
    public PropertyResponseDTO createProperty(PropertyCreateDTO dto) {
        Property property = mapper.toEntity(dto);

        UserResponseDTO currentUser = authService.getMe();
        User user = userService.findEntityById(currentUser.getId());
        property.setPublisher(user);

        Property saved = repository.save(property);
        return mapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<PropertyResponseDTO> findAll(PropertyFilterDTO filter) {
        List<Property> properties = repository.findAll(PropertySpecs.usingFilter(filter));

        return properties.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PropertyResponseDTO findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow((PropertyNotFoundException :: new));
    }

    @Transactional
    public PropertyResponseDTO updateProperty(UUID id, PropertyUpdateDTO dto) {
        Property property = repository.findById(id)
                .orElseThrow((PropertyNotFoundException :: new));

        mapper.updatePropertyFromDTO(dto, property);

        return mapper.toDTO(repository.save(property));
    }

    @Transactional
    public void deleteProperty(UUID id) {
        if (!repository.existsById(id)) {
            throw new PropertyNotFoundException();
        }
        repository.deleteById(id);
    }
}