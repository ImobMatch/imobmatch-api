package br.com.imobmatch.api.controllers;

import br.com.imobmatch.api.dtos.owner.OwnerResponseDTO;
import br.com.imobmatch.api.dtos.property.FeedResponseDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/feed")
public class feedController {

    private final BrokerFeedServiceImpl propertyViewHistoryService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN', 'BROKER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Page<FeedResponseDTO>> getPersonalizedFeed(
            @PageableDefault(size = 10, sort = "publicationDate", direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable UUID id) {

        Page<FeedResponseDTO> feed = propertyViewHistoryService.getRecommendationsForUser(id, pageable);

        return ResponseEntity.ok(feed);
    }
}
