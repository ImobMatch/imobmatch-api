package br.com.imobmatch.api.controllers;

import br.com.imobmatch.api.dtos.property.PropertyCreateDTO;
import br.com.imobmatch.api.dtos.property.PropertyFilterDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.dtos.property.PropertyUpdateDTO;
import br.com.imobmatch.api.services.property.PropertyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<PropertyResponseDTO>> getAll(PropertyFilterDTO filter) {
        return ResponseEntity.ok(service.findAll(filter));
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
            @RequestBody @Valid PropertyUpdateDTO dto
    ) {
        return ResponseEntity.ok(service.updateProperty(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'BROKER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}