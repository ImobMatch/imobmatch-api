package br.com.imobmatch.api.controllers;

import br.com.imobmatch.api.dtos.property.PropertiesImageDTO;
import br.com.imobmatch.api.dtos.property.PropertyCreateDTO;
import br.com.imobmatch.api.dtos.property.PropertyFilterDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.dtos.property.PropertyUpdateDTO;
import br.com.imobmatch.api.dtos.property.UploadImagenResponseDTO;
import br.com.imobmatch.api.dtos.user.UploadProfileImageResponse;
import br.com.imobmatch.api.services.property.PropertyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
@Tag(name = "Properties", description = "Endpoints for properties management")
public class PropertyController {

    private final PropertyService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'BROKER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PropertyResponseDTO> create(@RequestBody @Valid PropertyCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createProperty(dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'BROKER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<PropertyResponseDTO>> getAll(
            PropertyFilterDTO filter,
            @PageableDefault(page = 0, size = 10, sort = "publicationDate") Pageable pageable) {
        return ResponseEntity.ok(service.findAll(filter, pageable));
    }

    @GetMapping("/publisher/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'BROKER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<PropertyResponseDTO>> getPropertyByPublisherId(
            @PathVariable UUID id,
            @PageableDefault(page = 0, size = 10, sort = "publicationDate") Pageable pageable) {
        return ResponseEntity.ok(service.findPropertyByPublisherId(id, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'BROKER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PropertyResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'BROKER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<PropertyResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid PropertyUpdateDTO dto) {
        return ResponseEntity.ok(service.updateProperty(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'BROKER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/image")
    @PreAuthorize("hasAnyRole('BROKER', 'OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<byte[]> downloadProfileImage(
            @RequestBody PropertiesImageDTO dto) {
        return ResponseEntity.ok(this.service.downloadImage(dto));
    }

    @PostMapping(value = "/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('BROKER', 'OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UploadImagenResponseDTO> uploadImage(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(service.uploadImagen(id, file));
    }

    @DeleteMapping(value = "/image/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('BROKER', 'OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UploadProfileImageResponse> deleteProfileImage(
            @RequestBody PropertiesImageDTO dto) {
        this.service.removeImagen(dto);
        return ResponseEntity.ok().build();
    }
}