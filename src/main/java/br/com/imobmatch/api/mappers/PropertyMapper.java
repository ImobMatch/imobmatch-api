package br.com.imobmatch.api.mappers;

import br.com.imobmatch.api.dtos.property.FeedResponseDTO;
import br.com.imobmatch.api.dtos.property.PropertyCreateDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.dtos.property.PropertyUpdateDTO;
import br.com.imobmatch.api.models.property.Address;
import br.com.imobmatch.api.models.property.Property;
import br.com.imobmatch.api.models.property.PropertyImage;
import org.mapstruct.*;

import java.util.List;

@Mapper( componentModel = "spring",
        uses = {AddressMapper.class, PropertyCharacteristicMapper.class,
        CondominiumMapper.class, PropertyImageMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PropertyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "publicationDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "isAvailable", qualifiedByName = "mapToBoolean")
    Property toEntity(PropertyCreateDTO propertyCreateDTO);

    @Mapping(source = "publisher.id", target = "publisher")
    @Mapping(target = "isAvailable", qualifiedByName = "mapToBoolean")
    @Mapping(source = "imagens", target = "images")
    PropertyResponseDTO toDTO(Property property);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "isAvailable", qualifiedByName = "mapToBoolean")
    @Mapping(target = "publicationDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)

    void updatePropertyFromDTO(PropertyUpdateDTO propertyUpdateDTO, @MappingTarget Property entity);
    //maps for property
    @Mapping(target = "sale", source = "salePrice")
    @Mapping(target = "rent", source = "rentPrice")
    @Mapping(target = "size", source = "characteristic.area")
    @Mapping(target = "bedrooms", source = "characteristic.numBedrooms")
    @Mapping(target = "suites", source = "characteristic.numSuites")
    @Mapping(target = "bathrooms", source = "characteristic.numBathrooms")
    @Mapping(target = "garages", source = "characteristic.numGarageSpaces")
    @Mapping(target = "updatedAt", expression = "java(property.getUpdatedDate() != " +
            "null ? property.getUpdatedDate() : property.getPublicationDate())")
    @Mapping(target = "location", source = "address", qualifiedByName = "formatLocation")
    @Mapping(target = "image", source = "imagens", qualifiedByName = "getFirstImage")
    FeedResponseDTO toFeedDTO(Property property);

    @Named("formatLocation")
    default String formatLocation(Address address) {
        if (address == null) return "";
        return String.format("%s, %s - %s",
                address.getNeighborhood(),
                address.getCity(),
                address.getState());
    }

    @Named("getFirstImage")
    default String getFirstImage(List<PropertyImage> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }

        PropertyImage firstImage = images.get(0);

        if (firstImage == null || firstImage.getId() == null) {
            return null;
        }

        return firstImage.getId().toString();
    }

    default java.time.LocalDate resolveDate(Property p) {
        return p.getUpdatedDate() != null ? p.getUpdatedDate() : p.getPublicationDate();
    }

    @Named("mapToBoolean")
    default Boolean mapBoolean(Boolean value) {
        return value != null ? value : false;
    }
}
