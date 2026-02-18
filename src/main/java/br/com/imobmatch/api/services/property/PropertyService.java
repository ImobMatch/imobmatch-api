package br.com.imobmatch.api.services.property;

import br.com.imobmatch.api.dtos.property.PropertiesImageDTO;
import br.com.imobmatch.api.dtos.property.PropertyCreateDTO;
import br.com.imobmatch.api.dtos.property.PropertyFilterDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.dtos.property.PropertyUpdateDTO;
import br.com.imobmatch.api.dtos.property.UploadImagenResponseDTO;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PropertyService {

    PropertyResponseDTO createProperty(PropertyCreateDTO propertyCreateDTO);

    Page<PropertyResponseDTO> findAll(PropertyFilterDTO filter, Pageable pageable);

    PropertyResponseDTO findById(UUID id);

    PropertyResponseDTO updateProperty(UUID id, PropertyUpdateDTO propertyUpdateDTO);

    void deleteProperty(UUID id);

    UploadImagenResponseDTO uploadImagen(UUID id, MultipartFile file);

    byte[] downloadImage(PropertiesImageDTO dto);

    void removeImagen(PropertiesImageDTO dto);
}
