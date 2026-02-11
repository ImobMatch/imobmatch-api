package br.com.imobmatch.api.controllers;

import br.com.imobmatch.api.dtos.broker.BrokerResponseDTO;
import br.com.imobmatch.api.services.broker.BrokerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/brokers")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Endpoints for admin management")
public class AdminBrokerController {

    private final BrokerService brokerService;

    @GetMapping("/pending")
    public ResponseEntity<List<BrokerResponseDTO>> getPending() {
        return ResponseEntity.ok(brokerService.getPendingBrokers());
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<Void> approve(@PathVariable UUID id) {
        brokerService.approveBroker(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<Void> reject(@PathVariable UUID id) {
        brokerService.rejectBroker(id);
        return ResponseEntity.noContent().build();
    }

}