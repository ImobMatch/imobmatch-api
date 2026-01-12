package br.com.imobmatch.api.controllers;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.owner.OwnerUpdateDTO;
import br.com.imobmatch.api.dtos.owner.OwnerCreateDTO;
import br.com.imobmatch.api.dtos.owner.OwnerResponseDTO;
import br.com.imobmatch.api.services.owner.OwnerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

/**
 * <p>ontroller responsible for the <code>owner</code> entity's endpoints.</p>
 * <p>All endpoints require an authenticated user, except for the POST endpoint.<br>
 * URL-BASE = <code>/owners</code> for <code>POST </code>, <code>PATCH</code>, <code>GET</code>.<br>
 * DELETE-URL = <code>/delete-confirm</code></p>
 */
@RestController
@RequestMapping("/owners")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    /**
     * Create a new owner associated a new user
     *
     * @param ownerCreateDTO Data requested for the endpoint.
     *                The requested data is:<code>email</code>, <code>password</code>, <code>name</code>,
     *                <code>cpf</code>, <code>birthDate</code><code>phoneDdd</code>. <code>phoneNumber</code>
     *                All are not null
     *
     * @return Return ResponseDTO contains: <code>name</code>, <code>cpf</code>, <code>email</code>
     *         <code>phoneNumber</code>, <code>phoneDdd</code>, <code>birthDate</code>,
     *         <code>isEmailVerified</code>
     * @apiNote <p>This endpoint assumes the user has not been created previously.
     * The data sent is validated and exceptions are returned in case of violation or incorrect formatting.
     * If the value does not exist, it is assumed that it will not be updated.</p>
     * <p> <code>cpf</code> and <code>email</code> address undergo a format validation process.
     * <code>birthDate</code> of  cannot be from the future.</p>
     *
     */
    @PostMapping()
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<OwnerResponseDTO> createOwner(@Valid @RequestBody OwnerCreateDTO ownerCreateDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.ownerService.createOwner(ownerCreateDTO));
    }


    /**
     * Update authenticated owner data.
     *
     * @param ownerUpdateDTO New data for update. Only data unique to the owner entity can be updated.
     *               Possible data to be entered are: <code>name</code>,<code>phoneNumber</code>,
     *               <code>phoneDdd</code>, <code>birthDate</code>.
     * @return Return ResponseDTO contains: <code>name</code>, <code>cpf</code>, <code>email</code>
     *      <code>phoneNumber</code>, <code>phoneDdd</code>, <code>birthDate</code>,
     *      <code>isEmailVerified</code>
     *
     * @apiNote You only need to send the data that will be updated.
     * It is not necessary to specify null values.
     * If the value does not exist, it is assumed that it will not be updated.
     * If all requested data are null, an exception is returned.
     */
    @PatchMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<OwnerResponseDTO> updateOwner(@RequestBody OwnerUpdateDTO ownerUpdateDTO){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ownerService.updateOwner(ownerUpdateDTO));
    }

    /**
     * Get data from an authenticated owner.
     *
     * @return Return ResponseDTO contains: <code>name</code>, <code>cpf</code>, <code>email</code>
     *         <code>phoneNumber</code>, <code>phoneDdd</code>, <code>birthDate</code>,
     *         <code>isEmailVerified</code>
     *
     * @apiNote The endpoint doesn't need a body or parameters. But the owner needs to be logged in.
     */
    @GetMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<OwnerResponseDTO> getOwner(){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ownerService.getOwner());
    }

    /**
     * Permanently removes an <code>owner</code> and their <code>user</code> from the system.
     * You must be logged into the system and submit the user's password to confirm the operation.
     *
     * @PasswordUserDeleteDto User <code>password</code>
     * @apiNote The exclusion do cannot be undone.
     * <p>This operation will fail if the owner has any properties  or any other entity linked to them.
     * The user is logged out of the system after the operation is successful.</p>
     */
    @DeleteMapping("/confirm-delete")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<OwnerResponseDTO> deleteOwner(@RequestBody PasswordUserDeleteDTO passwordUserDeleteDTO){

        ownerService.deleteOwner(passwordUserDeleteDTO);
        return ResponseEntity.noContent().build();
    }

}
