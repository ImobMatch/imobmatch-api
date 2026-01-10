package br.com.imobmatch.api.controllers;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.owner.OwnerGetResponseDto;
import br.com.imobmatch.api.dtos.owner.OwnerPostDTO;
import br.com.imobmatch.api.dtos.owner.OwnerPatchDTO;
import br.com.imobmatch.api.dtos.owner.OwnerResponseDTO;
import br.com.imobmatch.api.services.owner.OwnerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>ontroller responsible for the <code>owner</code> entity's endpoints.</p>
 * <p>All endpoints require an authenticated user, except for the POST endpoint.<br>
 * URL-BASE = <code>/owner</code> for <code>POST </code>, <code>PATCH</code>, <code>GET</code>.<br>
 * DELETE-URL = <code>/delete-confirm</code></p>
 */
@RestController
@RequestMapping("/owner")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    /**
     * Create a new owner associated a new user
     *
     * @param postDto Data requested for the endpoint.
     *                The requested data is:<code>email</code>, <code>password</code>, <code>name</code>,
     *                <code>cpf</code>, <code>phoneNumber</code> <code>phoneDddNumber</code>
     *                <code>phoneNumber</code><code>isPrimaryPhone</code>;
     *                All are not null, except for the telephone, which are optional.
     *
     * @return <code>Name</code> and <code>Id</code> of created user for confirm operation
     * @apiNote <p>This endpoint assumes the user has not been created previously.
     * The data sent is validated and exceptions are returned in case of violation or incorrect formatting.
     * If the value does not exist, it is assumed that it will not be updated.</p>
     * <p>If a phone number is provided, all information should be entered.
     * If no phone number is provided, then all information should be null.
     * If this condition is violated, an exception is thrown.</p>
     *
     */
    @PostMapping()
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<OwnerResponseDTO> createOwner(@Valid @RequestBody OwnerPostDTO postDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.ownerService.createOwner(postDto));
    }


    /**
     * Update authenticated owner data.
     *
     * @param putDto New data for update. Only data unique to the owner entity can be updated.
     *               Possible data to be entered are: <code>name</code>
     * @return <code>Name</code> and <code>Id</code> of created user for confirm operation
     *
     * @apiNote You only need to send the data that will be updated.
     * It is not necessary to specify null values.
     * If the value does not exist, it is assumed that it will not be updated.
     * If all requested data are null, an exception is returned.
     */
    @PatchMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<OwnerResponseDTO> updateOwner(@RequestBody OwnerPatchDTO putDto){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ownerService.updateOwner(putDto));
    }

    /**
     * Get data from an authenticated owner.
     *
     * @return A more detailed information about the owner.
     * The data returned is: <code>id</code>, <code>name</code>, <code>cpf</code>,
     * <code>email</code>, <code>role</code>, <code>primaryPhone</code>;
     * If the <code>primaryPhone</code> does not exist, it returns an empty string ""
     *
     * @apiNote The endpoint doesn't need a body or parameters.
     * it simply returns the non-sensitive information of the authenticated user owner.
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
     * Permanently removes an <code>owner</code> and their <code>user</code> from the system.
     * You must be logged into the system and submit the user's password to confirm the operation.
     *
     * @PasswordUserDeleteDto User <code>password</code>
     * @apiNote The exclusion do cannot be undone.
     * <p>This operation will fail if the owner has any properties  or any other entity linked to them.</p>
     * <p>BEWARE: This endpoint does not guarantee the integrity of the deletion if the owner is linked to a property
     * or any other entity outside their User domain. However, it guarantees integrity if they are not.</p>
     */
    @DeleteMapping("/confirm-delete")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<OwnerResponseDTO> deleteOwner(@RequestBody PasswordUserDeleteDTO passwordConfirm){

        ownerService.deleteOwner(passwordConfirm);
        return ResponseEntity.noContent().build();
    }

}
