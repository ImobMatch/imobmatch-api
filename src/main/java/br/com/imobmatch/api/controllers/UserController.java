package br.com.imobmatch.api.controllers;

import br.com.imobmatch.api.dtos.email.RequestValidationEmailDTO;
import br.com.imobmatch.api.dtos.email.RequestValidationEmailResponseDTO;
import br.com.imobmatch.api.dtos.email.ValidateEmailRequestDTO;
import br.com.imobmatch.api.dtos.email.ValidateEmailResponseDTO;
import br.com.imobmatch.api.dtos.password.RequestPasswordResetDTO;
import br.com.imobmatch.api.dtos.password.ResetPasswordDTO;
import br.com.imobmatch.api.dtos.password.StatusPasswordResetDTO;
import br.com.imobmatch.api.dtos.user.UploadProfileImageResponse;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.services.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints for users management")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('BROKER', 'OWNER')")
    public ResponseEntity<UserResponseDTO> getMeUserAuthentication() {
        return ResponseEntity.ok(this.userService.getMe());
    }

    @PostMapping("/send-password-code")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> sendEmailCodeForPassword(@RequestBody RequestPasswordResetDTO request) {
        this.userService.requestPasswordReset(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<StatusPasswordResetDTO> swapPassword(@RequestBody ResetPasswordDTO request) {
        return ResponseEntity.ok(this.userService.resetPassword(request));
    }

    @PostMapping("/send-email-code")
    public ResponseEntity<RequestValidationEmailResponseDTO> sendEmailCode(@RequestBody RequestValidationEmailDTO request) {
        return ResponseEntity.ok(userService.sendEmailVerification(request));
    }

    @PostMapping("/validate-email")
    public ResponseEntity<ValidateEmailResponseDTO> validateEmail(@RequestBody ValidateEmailRequestDTO request) {
        return ResponseEntity.ok(this.userService.validateEmail(request));
    }

    @GetMapping("/profile-image/me")
    @PreAuthorize("hasAnyRole('BROKER', 'OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<byte[]> downloadProfileImageME() {
        return ResponseEntity.ok(this.userService.downloadProfileME());
    }

    @GetMapping("/profile-image/{key}")
    @PreAuthorize("hasAnyRole('BROKER', 'OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<byte[]> downloadProfileImage(@PathVariable String key) {
        return ResponseEntity.ok(this.userService.downloadProfile(key));
    }

    @PostMapping(value = "/profile-image/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('BROKER', 'OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UploadProfileImageResponse> uploadProfileImage(
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(userService.uploadProfileImage(file));
    }

    @DeleteMapping(value = "/profile-image/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('BROKER', 'OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UploadProfileImageResponse> deleteProfileImage(

    ) {
        userService.removeProfileImage();
        return ResponseEntity.ok().build();
    }


    @PatchMapping
    @PreAuthorize("hasAnyRole('BROKER', 'OWNER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UploadProfileImageResponse> updateProfileImage(
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(userService.uploadProfileImage(file));
    }





}
