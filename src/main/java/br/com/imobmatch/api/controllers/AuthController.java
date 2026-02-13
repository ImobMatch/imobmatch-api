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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.imobmatch.api.dtos.auth.AuthenticationDTO;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Endpoints for auth management")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){
        return ResponseEntity.ok(this.authService.login(data));
    }

    @PostMapping("/refresh")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<LoginResponseDTO> refreshToken(
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        LoginResponseDTO response = authService.refreshToken(token);
        return ResponseEntity.ok(response);
    }



}
