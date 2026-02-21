package br.com.imobmatch.api.services.property;

import br.com.imobmatch.api.dtos.property.PropertiesImageDTO;
import br.com.imobmatch.api.dtos.property.PropertyCreateDTO;
import br.com.imobmatch.api.dtos.property.PropertyFilterDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.dtos.property.PropertyUpdateDTO;
import br.com.imobmatch.api.dtos.property.UploadImagenResponseDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.auth.AuthenticationException;
import br.com.imobmatch.api.exceptions.property.PropertyIllegalBusinessPriceValueException;
import br.com.imobmatch.api.exceptions.property.PropertyNotFoundException;
import br.com.imobmatch.api.exceptions.property.PropertyNotUploadImagen;
import br.com.imobmatch.api.infra.s3.service.S3Service;
import br.com.imobmatch.api.mappers.PropertyMapper;
import br.com.imobmatch.api.models.enums.PropertyBusinessType;
import br.com.imobmatch.api.models.property.Property;
import br.com.imobmatch.api.models.property.PropertyImage;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.repositories.BrokerRepository;
import br.com.imobmatch.api.repositories.PropertiesImagesRepository;
import br.com.imobmatch.api.repositories.PropertyRepository;
import br.com.imobmatch.api.services.auth.AuthService;
import br.com.imobmatch.api.services.feed.broker.BrokerFeedServiceImpl;
import br.com.imobmatch.api.services.user.UserService;
import br.com.imobmatch.api.specs.property.PropertySpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final BrokerFeedServiceImpl viewService;
    private final PropertyRepository repository;
    private final PropertyMapper mapper;
    private final UserService userService;
    private final BrokerRepository brokerRepository;
    private final AuthService authService;
    private final PropertiesImagesRepository propertiesImagesRepository;
    private final S3Service s3Service;
    private int numberOfPropertiesImages = 15;

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
    public Page<PropertyResponseDTO> findAll(PropertyFilterDTO filter, Pageable pageable) {
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

    @Override
    public Page<PropertyResponseDTO> findPropertyByPublisherId(UUID publisherId, Pageable pageable) {
        return repository.findAllByPublisher_Id(publisherId, pageable)
                .map(mapper :: toDTO);
    }

    @Transactional
    public PropertyResponseDTO updateProperty(UUID id, PropertyUpdateDTO dto) {
        Property property = repository.findById(id)
                .orElseThrow(PropertyNotFoundException::new);

        mapper.updatePropertyFromDTO(dto, property);

        switch (property.getBusinessType()) {
            case SALE -> property.setRentPrice(null);
            case RENT -> property.setSalePrice(null);
            case SALE_AND_RENT -> {
            }
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

    @Override
    public UploadImagenResponseDTO uploadImagen(UUID id, MultipartFile file) {
        UserResponseDTO user = this.userService.getMe();
        Property property = repository.findById(id).orElseThrow(PropertyNotFoundException::new);
        if (!property.getPublisher().getId().equals(user.getId())) {
            throw new AuthenticationException();
        }

        if (this.propertiesImagesRepository.countByPropertyId(property.getId()) >= this.numberOfPropertiesImages) {
            throw new PropertyNotUploadImagen();
        }

        try {
            byte[] bytes = file.getBytes();
            String key = this.s3Service.uploadPropertyImage(property.getId(), bytes);
            PropertyImage image = new PropertyImage();

            image.setProperty(property);
            image.setImagenKey(key);
            this.propertiesImagesRepository.save(image);
            return new UploadImagenResponseDTO(property.getId(), image.getId());

        } catch (IOException e) {
            throw new RuntimeException("ERROR PROPERTY IMAGEn READ", e);
        }

    }

    @Override
    public byte[] downloadImage(PropertiesImageDTO dto) {
        UUID id = dto.id();
        UUID imageId = dto.imageId();
        repository.findById(id).orElseThrow(PropertyNotFoundException::new);
        PropertyImage p = this.propertiesImagesRepository.findById(imageId).orElseThrow(PropertyNotFoundException::new);
        return this.s3Service.downloadPropertyImage(p.getImagenKey());
    }

    @Override
    public void removeImagen(PropertiesImageDTO dto) {
        UUID id = dto.id();
        UUID imageId = dto.imageId();
        UserResponseDTO user = this.userService.getMe();
        Property property = repository.findById(id).orElseThrow(PropertyNotFoundException::new);
        PropertyImage p = this.propertiesImagesRepository.findById(imageId).orElseThrow(PropertyNotFoundException::new);

        if (!user.getId().equals(property.getPublisher().getId())) {
            throw new AuthenticationException();
        }

        if (!p.getProperty().equals(property)) {
            throw new PropertyNotUploadImagen();
        }

        this.propertiesImagesRepository.delete(p);
    }

    private void validateBusinessValue(PropertyBusinessType type, BigDecimal rentPrice, BigDecimal salePrice) {
        if (type == null) {
            throw new PropertyIllegalBusinessPriceValueException();
        }
        switch (type) {
            case SALE -> {
                if (salePrice == null || rentPrice != null) {
                    throw new PropertyIllegalBusinessPriceValueException();
                }
            }
            case RENT -> {
                if (rentPrice == null || salePrice != null) {
                    throw new PropertyIllegalBusinessPriceValueException();
                }
            }
            case SALE_AND_RENT -> {
                if (salePrice == null || rentPrice == null) {
                    throw new PropertyIllegalBusinessPriceValueException();
                }
            }
        }
    }
}