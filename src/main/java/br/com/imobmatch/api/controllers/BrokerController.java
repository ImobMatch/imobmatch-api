package br.com.imobmatch.api.controllers;

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
import br.com.imobmatch.api.dtos.broker.BrokerPostDTO;
import br.com.imobmatch.api.dtos.broker.BrokerResponseDTO;
import org.springframework.http.MediaType;
import br.com.imobmatch.api.dtos.broker.*;
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
    private final BrokerValidationService brokerValidationService;

    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<BrokerResponseDTO> createBroker(@Valid @RequestBody BrokerPostDTO brokerPostDTO) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(this.brokerService.createBroker(brokerPostDTO));
    }


@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BrokerResponseDTO> create(
            @RequestPart("data") @Valid BrokerPostDTO data, // @Valid ativa o @CPF do seu DTO
            @RequestPart("cpfFile") MultipartFile cpfFile,
            @RequestPart("creciFile") MultipartFile creciFile
    ) {
        
        brokerValidationService.run(data, cpfFile, creciFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    public ResponseEntity<BrokerResponseDTO> getBroker() {
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(brokerService.getBroker());
    }

    @DeleteMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('BROKER')")
    public ResponseEntity<BrokerResponseDTO> deleteBroker(@RequestBody PasswordUserDeleteDTO passwordUserDeleteDTO){

        brokerService.deleteBroker(passwordUserDeleteDTO);
        return ResponseEntity.noContent().build();
    }
    
}
