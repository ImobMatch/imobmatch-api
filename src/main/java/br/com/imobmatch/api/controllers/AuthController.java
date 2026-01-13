package br.com.imobmatch.api.controllers;

import br.com.imobmatch.api.dtos.auth.LoginResponseDTO;
import br.com.imobmatch.api.dtos.email.RequestValidationEmailDTO;
import br.com.imobmatch.api.dtos.email.RequestValidationEmailResponseDTO;
import br.com.imobmatch.api.dtos.email.ValidateEmailRequestDTO;
import br.com.imobmatch.api.dtos.email.ValidateEmailResponseDTO;
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

    @PostMapping("/send-email-code")
    public RequestValidationEmailResponseDTO sendEmailCode(@RequestBody RequestValidationEmailDTO request) {
        return userService.sendEmailVerificationCodeForEmail(request);
    }

    @PostMapping("/validate-email")
    public ValidateEmailResponseDTO validateEmail(@RequestBody ValidateEmailRequestDTO request) {
        return userService.validateEmail(request);
    }

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
    public ResponseEntity<LoginResponseDTO> refresh(
            @RequestHeader("Authorization") String authorization
    ) {
        String token = authorization.replace("Bearer ", "");
        return ResponseEntity.ok(authService.refreshToken(token));
    }

}
