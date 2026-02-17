package br.com.imobmatch.api.services.property;

import br.com.imobmatch.api.dtos.property.PropertyCreateDTO;
import br.com.imobmatch.api.dtos.property.PropertyFilterDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.dtos.property.PropertyUpdateDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.property.PropertyIllegalBusinessPriceValueException;
import br.com.imobmatch.api.exceptions.property.PropertyNotFoundException;
import br.com.imobmatch.api.mappers.PropertyMapper;
import br.com.imobmatch.api.models.enums.PropertyBusinessType;
import br.com.imobmatch.api.models.property.Property;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.repositories.BrokerRepository;
import br.com.imobmatch.api.repositories.PropertyRepository;
import br.com.imobmatch.api.services.auth.AuthService;
import br.com.imobmatch.api.services.feed.broker.BrokerFeedServiceImpl;
import br.com.imobmatch.api.services.user.UserService;
import br.com.imobmatch.api.specs.property.PropertySpecs;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private  final BrokerFeedServiceImpl viewService;
    private final PropertyRepository repository;
    private final PropertyMapper mapper;
    private final UserService userService;
    private final BrokerRepository  brokerRepository;
    private final AuthService authService;

    @Transactional
    public PropertyResponseDTO createProperty(PropertyCreateDTO dto) {

        Property property = mapper.toEntity(dto);

        UserResponseDTO currentUser = authService.getMe();
        User user = userService.findEntityById(currentUser.getId());
        property.setPublisher(user);
        validateBusinessValue(dto.getBusinessType(), dto.getRentPrice(), dto.getSalePrice());
        Property saved = repository.save(property);
        return mapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public Page<PropertyResponseDTO> findAll(PropertyFilterDTO filter, Pageable pageable){
        Page<Property> propertiesPage = repository.findAll(PropertySpecs.usingFilter(filter), pageable);

        return propertiesPage.map(mapper::toDTO);
    }

    @Transactional()
    public PropertyResponseDTO findById(UUID id) {
        Property property = repository.findById(id)
                .orElseThrow(PropertyNotFoundException::new);
        UserResponseDTO currentUser = authService.getMe();
        brokerRepository.findById(currentUser.getId())
                .ifPresent(broker -> viewService.trackView(property, broker));

        return mapper.toDTO(property);
    }

    @Transactional
    public PropertyResponseDTO updateProperty(UUID id, PropertyUpdateDTO dto) {
        Property property = repository.findById(id)
                .orElseThrow(PropertyNotFoundException::new);

        mapper.updatePropertyFromDTO(dto, property);

        switch (property.getBusinessType()) {
            case SALE -> property.setRentPrice(null);
            case RENT -> property.setSalePrice(null);
            case SALE_AND_RENT -> {}
        }

        validateBusinessValue(property.getBusinessType(), property.getRentPrice(), property.getSalePrice());
        property.setUpdatedDate(LocalDate.now());

        return mapper.toDTO(repository.save(property));
    }

    @Transactional
    public void deleteProperty(UUID id) {
        if (!repository.existsById(id)) {
            throw new PropertyNotFoundException();
        }
        repository.deleteById(id);
    }

    private void validateBusinessValue(PropertyBusinessType type, BigDecimal rentPrice, BigDecimal salePrice) {
        if (type == null) {throw new PropertyIllegalBusinessPriceValueException();}
        switch (type) {
            case SALE -> {
                if (salePrice == null || rentPrice != null){throw new PropertyIllegalBusinessPriceValueException();}
            }
            case RENT -> {
                if (rentPrice == null || salePrice != null){throw new PropertyIllegalBusinessPriceValueException();}
            }
            case SALE_AND_RENT -> {
                if (salePrice == null || rentPrice == null) {
                    throw new PropertyIllegalBusinessPriceValueException();
                }
            }
        }
    }
}