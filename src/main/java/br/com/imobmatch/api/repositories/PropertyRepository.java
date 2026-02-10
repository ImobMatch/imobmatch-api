package br.com.imobmatch.api.repositories;

import br.com.imobmatch.api.models.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID>,
                                            JpaSpecificationExecutor<Property> {

    List<Property> findAllByPublisher_Id(UUID id);

}
