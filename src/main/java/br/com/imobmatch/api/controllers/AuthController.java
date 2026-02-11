package br.com.imobmatch.api.controllers;

import br.com.imobmatch.api.dtos.auth.LoginResponseDTO;
import br.com.imobmatch.api.dtos.email.RequestValidationEmailDTO;
import br.com.imobmatch.api.dtos.email.RequestValidationEmailResponseDTO;
import br.com.imobmatch.api.dtos.email.ValidateEmailRequestDTO;
import br.com.imobmatch.api.dtos.email.ValidateEmailResponseDTO;
import br.com.imobmatch.api.dtos.password.RequestPasswordResetDTO;
import br.com.imobmatch.api.dtos.password.ResetPasswordDTO;
import br.com.imobmatch.api.dtos.password.StatusPasswordResetDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.services.auth.AuthService;
import br.com.imobmatch.api.services.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.imobmatch.api.dtos.auth.AuthenticationDTO;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){
        return ResponseEntity.ok(this.authService.login(data));
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UserResponseDTO> me() {
        return  ResponseEntity.ok(this.authService.getMe());
    }

    @PostMapping("/refresh")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<LoginResponseDTO> refreshToken(
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        LoginResponseDTO response = authService.refreshToken(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-password-code")
    public ResponseEntity<?> sendEmailCodeForPassword(@RequestBody RequestPasswordResetDTO request) {
        this.userService.requestPasswordReset(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<StatusPasswordResetDTO> SwapPassoword(@RequestBody ResetPasswordDTO request) {
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




}
