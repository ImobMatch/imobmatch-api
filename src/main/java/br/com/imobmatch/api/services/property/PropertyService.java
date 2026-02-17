package br.com.imobmatch.api.services.property;

import br.com.imobmatch.api.dtos.property.PropertiesImageDTO;
import br.com.imobmatch.api.dtos.property.PropertyCreateDTO;
import br.com.imobmatch.api.dtos.property.PropertyFilterDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.dtos.property.PropertyUpdateDTO;
import br.com.imobmatch.api.dtos.property.UploadImagenResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


public interface PropertyService {

    PropertyResponseDTO createProperty(PropertyCreateDTO propertyCreateDTO);
    List<PropertyResponseDTO> findAll(PropertyFilterDTO propertyFilterDTO);
    PropertyResponseDTO findById(UUID id);
    PropertyResponseDTO updateProperty(UUID id, PropertyUpdateDTO propertyUpdateDTO);
    void deleteProperty(UUID id);

    UploadImagenResponseDTO uploadImagen(UUID id, MultipartFile file);
    byte[] downloadImage(PropertiesImageDTO dto);
    void removeImagen(PropertiesImageDTO dto);
}
