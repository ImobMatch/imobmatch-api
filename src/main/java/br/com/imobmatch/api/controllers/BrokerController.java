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
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<BrokerResponseDTO> createBroker(@Valid @RequestBody BrokerPostDTO brokerPostDTO) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(this.brokerService.createBroker(brokerPostDTO));
    }

    @PatchMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('BROKER')")
    public ResponseEntity<BrokerResponseDTO> updateBroker(@RequestBody BrokerPatchDTO brokerPatchDTO) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.updateBroker(brokerPatchDTO));
    }

    @GetMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('BROKER')")
    public ResponseEntity<BrokerResponseDTO> getMeBroker() {
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.getMeBroker());
    }

    @GetMapping("/id/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<BrokerResponseDTO> getByIdBroker(@Valid @RequestBody UUID id) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.getByIdBroker(id));
    }

    @GetMapping("/creci/{creci}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<BrokerResponseDTO> getByCreciBroker(@Valid @RequestBody String creci) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.getByCreciBroker(creci));
    }

    @GetMapping("/cpf/{cpf}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<BrokerResponseDTO> getByCpfBroker(@Valid @RequestBody String cpf) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.getByCpfBroker(cpf));
    }

    @GetMapping("/name/{name}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<BrokerResponseDTO>> ListByNameBroker(@Valid @RequestBody String name) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.ListByNameBroker(name));
    }

    @GetMapping("/region-interest/{regionInterest}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<BrokerResponseDTO>> ListByRegionInterestBroker(@Valid @RequestBody String regionInterest) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.ListByRegionInterestBroker(regionInterest));
    }

    @GetMapping("/operation-city/{operationCity}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<BrokerResponseDTO>> ListByOperationCityBroker(@Valid @RequestBody String operationCity) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.ListByOperationCityBroker(operationCity));
    }

    @GetMapping("/property-type/{propertyType}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<BrokerResponseDTO>> ListByPropertyTypeBroker(@Valid @RequestBody BrokerPropertyType propertyType) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.ListByPropertyTypeBroker(propertyType));
    }

    @GetMapping("/business-type/{businessType}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<BrokerResponseDTO>> ListByBusinessTypeBroker(@Valid @RequestBody BrokerBusinessType businessType) {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.ListByBusinessTypeBroker(businessType));
    }

    @GetMapping("/all")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<BrokerResponseDTO>> ListAllBroker() {

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.ListAllBroker());
    }

    @DeleteMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('BROKER')")
    public ResponseEntity<BrokerResponseDTO> deleteBroker(@RequestBody PasswordUserDeleteDTO passwordUserDeleteDTO){

        brokerService.deleteBroker(passwordUserDeleteDTO);
        return ResponseEntity.noContent().build();
    }
    
}
