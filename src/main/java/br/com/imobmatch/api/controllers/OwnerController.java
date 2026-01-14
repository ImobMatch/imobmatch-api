package br.com.imobmatch.api.controllers;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.owner.OwnerCreateDTO;
import br.com.imobmatch.api.dtos.owner.OwnerGetAllByResponseDTO;
import br.com.imobmatch.api.dtos.owner.OwnerResponseDTO;
import br.com.imobmatch.api.dtos.owner.OwnerUpdateDTO;
import br.com.imobmatch.api.services.owner.OwnerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * <p>Controller responsible for the <code>owner</code> entity's endpoints.</p>
 * <p>Endpoints are divided into three categories:
 * <ul>
 * <li><b>Public:</b> Registration (POST).</li>
 * <li><b>Authenticated User (/me):</b> Manage the currently logged-in owner profile.</li>
 * <li><b>Search & Management:</b> Broker or Admin views to list and find owners.</li>
 * </ul>
 * </p>
 */
@RestController
@RequestMapping("/owners")
@AllArgsConstructor
@Tag(name = "Owners", description = "Endpoints for owner management")
public class OwnerController {

    private final OwnerService ownerService;

    /**
     * Create a new owner associated with a new user.
     *
     * @param dto Data requested for the endpoint.
     * The requested data is: <code>email</code>, <code>password</code>, <code>name</code>,
     * <code>cpf</code>, <code>birthDate</code>, <code>whatsAppPhoneNumber</code>.
     * <code>personalPhoneNumber</code> is optional.
     *
     * @return Return ResponseDTO containing: <code>name</code>, <code>cpf</code>, <code>email</code>,
     * <code>whatsAppPhoneNumber</code>, <code>personalPhoneNumber</code>, <code>birthDate</code>,
     * <code>isEmailVerified</code>.
     *
     * @apiNote <p>This endpoint assumes the user has not been created previously.
     * The data sent is validated and exceptions are returned in case of violation or incorrect formatting.
     * <p> <code>cpf</code> and <code>email</code> address undergo a format validation process.
     * <code>birthDate</code> cannot be from the future.</p>
     */
    @PostMapping
    public ResponseEntity<OwnerResponseDTO> create(@Valid @RequestBody OwnerCreateDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.ownerService.createOwner(dto));
    }

    /**
     * Get data from the currently authenticated owner.
     *
     * @return Return ResponseDTO containing: <code>name</code>, <code>cpf</code>, <code>email</code>,
     * <code>whatsAppPhoneNumber</code>, <code>personalPhoneNumber</code>, <code>birthDate</code>, <code>isEmailVerified</code>.
     *
     * @apiNote The endpoint accesses the token to identify the user. URL: <code>/owners/me</code>.
     */
    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    public ResponseEntity<OwnerResponseDTO> getOwner() {
        return ResponseEntity.ok(ownerService.getOwner());
    }

    /**
     * Update authenticated owner data.
     *
     * @param dto New data for update. Only data unique to the owner entity can be updated.
     * Possible data to be entered are:Return ResponseDTO containing: <code>name</code>,
     * <code>whatsAppPhoneNumber</code>, <code>personalPhoneNumber</code>, <code>birthDate</code>,
     * @return Return ResponseDTO containing: <code>name</code>, <code>cpf</code>, <code>email</code>,
     *<code>whatsAppPhoneNumber</code>, <code>personalPhoneNumber</code>, <code>birthDate</code>,
     * <code>isEmailVerified</code>.
     *
     * @apiNote <p>URL: <code>/owners/me</code></p>
     * You only need to send the data that will be updated.
     * It is not necessary to specify null values.
     * If the value does not exist, it is assumed that it will not be updated.
     */
    @PatchMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    public ResponseEntity<OwnerResponseDTO> updateMe(@RequestBody OwnerUpdateDTO dto) {
        return ResponseEntity.ok(ownerService.updateOwner(dto));
    }

    /**
     * Permanently removes the authenticated <code>owner</code> and their <code>user</code> from the system.
     * <p>You must be logged into the system and submit the user's password to confirm the operation.</p>
     *
     * @param dto Object containing the User's <code>password</code> for confirmation.
     * @apiNote <p>URL: <code>/owners/me</code></p>
     * The exclusion cannot be undone.
     * <p>This operation will fail if the owner has any properties or any other entity linked to them.
     * The user is logged out of the system after the operation is successful.</p>
     */
    @DeleteMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    public ResponseEntity<Void> deleteMe(@RequestBody PasswordUserDeleteDTO dto) {
        ownerService.deleteOwner(dto);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieve a list of owners based on optional filters.
     * <p>If no parameters are provided, it returns all owners.</p>
     *
     * @param name (Optional) Filters by owner's name.
     * @param birthDate (Optional) Filters by owner's birthdate (Format: YYYY-MM-DD).
     * @return A list of owners wrapped in a DTO.
     *
     * @apiNote <p><b>Filter Priority:</b></p>
     * <ol>
     * <li>If <code>name</code> is provided, it returns owners matching the name.</li>
     * <li>If <code>birthDate</code> is provided (and name is null), it returns owners matching the date.</li>
     * <li>If neither is provided, returns all owners.</li>
     * <li>If no data is found, it returns an empty list.</li>
     * </ol>
     */
    @GetMapping()
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('BROKER', 'ADMIN')")
    public ResponseEntity<OwnerGetAllByResponseDTO> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDate birthDate
    ) {

        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(ownerService.getAllOwnersByName(name));
        }

        if (birthDate != null) {
            return ResponseEntity.ok(ownerService.getAllOwnersByBirthDate(birthDate));
        }

        return ResponseEntity.ok(ownerService.getAllOwners());
    }

    /**
     * Get specific owner data by their unique ID (UUID).
     *
     * @param id The UUID of the owner.
     * @return Return ResponseDTO containing: <code>name</code>, <code>cpf</code>, <code>email</code>,
     * <code>whatsAppPhoneNumber</code>, <code>personalPhoneNumber</code>, <code>birthDate</code>,
     * <code>isEmailVerified</code>.
     * @apiNote Throws 404 if the ID does not exist.
     */
    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('BROKER', 'ADMIN')")
    public ResponseEntity<OwnerResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ownerService.getOwnerById(id));
    }

    /**
     * Search for a single owner by their unique email address.
     *
     * @param email The email to search for.
     * @return Return ResponseDTO containing: <code>name</code>, <code>cpf</code>, <code>email</code>,
     *<code>whatsAppPhoneNumber</code>, <code>personalPhoneNumber</code>, <code>birthDate</code>,
     *<code>isEmailVerified</code>.
     * @apiNote Activated when the <code>email</code> query parameter is present.
     */
    @GetMapping(value = "/search", params = "email")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('BROKER', 'ADMIN')")
    public ResponseEntity<OwnerResponseDTO> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(ownerService.getOwnerByEmail(email));
    }

    /**
     * Search for a single owner by their unique CPF.
     *
     * @param cpf The CPF to search for (formatted or plain numbers).
     * @return Return ResponseDTO containing: <code>name</code>, <code>cpf</code>, <code>email</code>,
     *<code>whatsAppPhoneNumber</code>, <code>personalPhoneNumber</code>, <code>birthDate</code>,
     * <code>isEmailVerified</code>.
     * @apiNote Activated when the <code>cpf</code> query parameter is present.
     */
    @GetMapping(value = "/search", params = "cpf")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('BROKER', 'ADMIN')")
    public ResponseEntity<OwnerResponseDTO> getByCpf(@RequestParam String cpf) {
        return ResponseEntity.ok(ownerService.getOwnerByCpf(cpf));
    }
}