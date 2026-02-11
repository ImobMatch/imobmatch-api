package br.com.imobmatch.api.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;

import br.com.imobmatch.api.dtos.broker.*;
import br.com.imobmatch.api.models.enums.BrokerBusinessType;
import br.com.imobmatch.api.models.enums.BrokerPropertyType;
import br.com.imobmatch.api.services.broker.BrokerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.*;

@RestController
@RequestMapping("/brokers")
@AllArgsConstructor
public class BrokerController {

    private final BrokerService brokerService;

    @PostMapping
    public ResponseEntity<BrokerResponseDTO> create(
            @RequestBody @Valid BrokerPostDTO data) {
        BrokerResponseDTO response = brokerService.createBroker(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('BROKER')")
    public ResponseEntity<BrokerResponseDTO> updateMe(@RequestBody BrokerPatchDTO dto) {
        return ResponseEntity.ok(brokerService.updateBroker(dto));
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('BROKER')")
    public ResponseEntity<BrokerResponseDTO> getMe() {
        return ResponseEntity.ok(brokerService.getBroker());
    }

    @GetMapping("/search/id/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN','BROKER','OWNER')")
    public ResponseEntity<BrokerResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(brokerService.getBrokerById(id));
    }

    @GetMapping("/search/creci/{creci}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN','BROKER','OWNER')")
    public ResponseEntity<BrokerResponseDTO> getByCreci(@PathVariable String creci) {
        return ResponseEntity.ok(brokerService.getBrokerByCreci(creci));
    }

    @GetMapping("/search/cpf/{cpf}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN','BROKER','OWNER')")
    public ResponseEntity<BrokerResponseDTO> getByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(brokerService.getBrokerByCpf(cpf));
    }

    @GetMapping("/search/name/{name}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN','BROKER','OWNER')")
    public ResponseEntity<List<BrokerResponseDTO>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(brokerService.getBrokersByName(name));
    }

    @GetMapping("/search")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN','BROKER','OWNER')")
    public ResponseEntity<List<BrokerResponseDTO>> search(
            @RequestParam(required = false) String regionInterest,
            @RequestParam(required = false) String operationCity,
            @RequestParam(required = false) BrokerPropertyType propertyType,
            @RequestParam(required = false) BrokerBusinessType businessType) {
        return ResponseEntity.ok(
                brokerService.search(regionInterest, operationCity, propertyType, businessType));
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN','BROKER','OWNER')")
    public ResponseEntity<List<BrokerResponseDTO>> getAll() {
        return ResponseEntity.ok(brokerService.getAllBrokers());
    }

    @DeleteMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('BROKER')")
    public ResponseEntity<Void> deleteMe(@RequestBody PasswordUserDeleteDTO dto) {
        brokerService.deleteBroker(dto);
        return ResponseEntity.noContent().build();
    }
}
