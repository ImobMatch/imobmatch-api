package br.com.imobmatch.api.controllers;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDto;
import br.com.imobmatch.api.dtos.owner.OwnerGetResponseDto;
import br.com.imobmatch.api.dtos.owner.OwnerPostDto;
import br.com.imobmatch.api.dtos.owner.OwnerPatchDto;
import br.com.imobmatch.api.dtos.owner.OwnerResponseDto;
import br.com.imobmatch.api.services.owner.OwnerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    /**
     * Create a new owner associated a new user
     *
     * @param postDto data requested for the endpoint
     * @return name of created user for confirm
     */
    @PostMapping()
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<OwnerResponseDto> createOwner(@Valid @RequestBody OwnerPostDto postDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.ownerService.createOwner(postDto));
    }


    /**
     * update authenticated owner data
     *
     * @param putDto data requested for the endpoint
     * @return name of created user for confirm
     */
    @PatchMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<OwnerResponseDto> updateOwner(@RequestBody OwnerPatchDto putDto){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ownerService.updateOwner(putDto));
    }

    /**
     * Get data from an authenticated owner.
     *
     * @return A more details owner data
     */
    @GetMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<OwnerGetResponseDto> getAuthenticatedOwner(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ownerService.getAuthenticatedOwner());
    }

    /**
     * It permanently removes an owner and their user from the system.
     * You need to be logged into the system and re-enter your credentials.
     *
     * @PasswordUserDeleteDto user password for confirm delete
     * @return A confirm response dto
     */
    @DeleteMapping("/confirm-delete")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<OwnerResponseDto> deleteOwner(@RequestBody PasswordUserDeleteDto passwordComfirm){

        return ResponseEntity.noContent().build();
    }

}
