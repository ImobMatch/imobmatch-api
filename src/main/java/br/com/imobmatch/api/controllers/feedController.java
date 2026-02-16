package br.com.imobmatch.api.controllers;

import br.com.imobmatch.api.dtos.owner.OwnerGetAllByResponseDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.services.feed.broker.BrokerFeedServiceImpl;
import br.com.imobmatch.api.services.owner.OwnerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/feed")
public class feedController {

    private final BrokerFeedServiceImpl propertyViewHistoryService;
    private final OwnerService ownerService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'BROKER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<PropertyResponseDTO>> getPersonalizedFeed(
            @PageableDefault(size = 10, sort = "publicationDate", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable UUID id) {

        Page<PropertyResponseDTO> feed = propertyViewHistoryService.getRecommendationsForUser(id, pageable);

        return ResponseEntity.ok(feed);
    }

    //this is simply get all copied from ownerController
    @GetMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('BROKER', 'ADMIN')")
    public ResponseEntity<OwnerGetAllByResponseDTO> getOwnerFeed(){
        return ResponseEntity.ok(ownerService.getAllOwners());
    }
}
