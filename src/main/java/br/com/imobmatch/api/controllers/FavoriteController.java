package br.com.imobmatch.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.imobmatch.api.dtos.favorite.FavoriteResponseDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.services.favorite.FavoriteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/favorites")
@AllArgsConstructor
@Tag(name = "Favorites", description = "Endpoints for broker favorite system")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('BROKER', 'ADMIN')")
    public ResponseEntity<FavoriteResponseDTO> create(@RequestBody UUID propertyId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(favoriteService.favoriteProperty(propertyId));
    }

    @DeleteMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('BROKER', 'ADMIN')")
    public ResponseEntity<Void> delete(@RequestBody UUID propertyId) {
        favoriteService.unfavoriteProperty(propertyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('BROKER', 'ADMIN')")
    public ResponseEntity<List<FavoriteResponseDTO>> getAll() {
        return ResponseEntity.ok(
                favoriteService.getAllBrokerFavorites());
    }

    @GetMapping("/processed")
    public ResponseEntity<List<PropertyResponseDTO>> getAllProcessed() {
        return ResponseEntity.ok(
                favoriteService.getAllBrokerFavoriteProperties());
    }
}
