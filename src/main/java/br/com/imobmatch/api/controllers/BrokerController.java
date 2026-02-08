package br.com.imobmatch.api.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;

import org.springframework.http.MediaType;
import br.com.imobmatch.api.dtos.broker.*;
import br.com.imobmatch.api.models.enums.BrokerAccountStatus;
import br.com.imobmatch.api.models.enums.BrokerBusinessType;
import br.com.imobmatch.api.models.enums.BrokerPropertyType;
import br.com.imobmatch.api.services.broker.BrokerService;
import br.com.imobmatch.api.services.broker.BrokerValidationService;
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
            @RequestBody @Valid BrokerPostDTO data
    ) {
        // Chamada atualizada sem os arquivos
        BrokerResponseDTO response = this.brokerService.createBroker(data);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }


    @PatchMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('BROKER')")
    public ResponseEntity<BrokerResponseDTO> updateMe(@RequestBody BrokerPatchDTO brokerPatchDTO) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.updateBroker(brokerPatchDTO));
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('BROKER')")
    public ResponseEntity<BrokerResponseDTO> getMe() {
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.getBroker());
    }

    @GetMapping(value = "/search", params = "id")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'BROKER', 'OWNER')")
    public ResponseEntity<BrokerResponseDTO> getById(@Valid @RequestBody UUID id) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.getBrokerById(id));
    }

    @GetMapping(value = "/search", params = "creci")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'BROKER', 'OWNER')")
    public ResponseEntity<BrokerResponseDTO> getByCreci(@Valid @RequestBody String creci) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.getBrokerByCreci(creci));
    }

    @GetMapping(value = "/search", params = "cpf")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'BROKER', 'OWNER')")
    public ResponseEntity<BrokerResponseDTO> getByCpf(@Valid @RequestBody String cpf) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.getBrokerByCpf(cpf));
    }

    @GetMapping(value = "/search", params = "name")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'BROKER', 'OWNER')")
    public ResponseEntity<List<BrokerResponseDTO>> getByName(@Valid @RequestBody String name) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.getBrokersByName(name));
    }

    @GetMapping(value = "/search", params = "regionInterest")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'BROKER', 'OWNER')")
    public ResponseEntity<List<BrokerResponseDTO>> getByRegionInterest(@Valid @RequestBody String regionInterest) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.getBrokersByRegionInterest(regionInterest));
    }

    @GetMapping(value = "/search", params = "operationCity")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'BROKER', 'OWNER')")
    public ResponseEntity<List<BrokerResponseDTO>> getByOperationCity(@Valid @RequestBody String operationCity) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.getBrokersByOperationCity(operationCity));
    }

    @GetMapping(value = "/search", params = "propertyType")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'BROKER', 'OWNER')")
    public ResponseEntity<List<BrokerResponseDTO>> getByPropertyType(@Valid @RequestBody BrokerPropertyType propertyType) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.getBrokersByPropertyType(propertyType));
    }

    @GetMapping(value = "/search", params = "businessType")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'BROKER', 'OWNER')")
    public ResponseEntity<List<BrokerResponseDTO>> getByBusinessType(@Valid @RequestBody BrokerBusinessType businessType) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.getBrokersByBusinessType(businessType));
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN', 'BROKER', 'OWNER')")
    public ResponseEntity<List<BrokerResponseDTO>> getAll() {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.getAllBrokers());
    }

    @DeleteMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('BROKER')")
    public ResponseEntity<BrokerResponseDTO> deleteMe(@RequestBody PasswordUserDeleteDTO passwordUserDeleteDTO){

        brokerService.deleteBroker(passwordUserDeleteDTO);
        return ResponseEntity.noContent().build();
    }
}
