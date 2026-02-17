package br.com.imobmatch.api.repositories;

import br.com.imobmatch.api.models.property.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PropertiesImagesRepository extends JpaRepository<PropertyImage, UUID> {

    long countByPropertyId(UUID propertyId);
}