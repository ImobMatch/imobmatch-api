package br.com.imobmatch.api.dtos.auth;

import br.com.imobmatch.api.models.enums.TokenType;
import br.com.imobmatch.api.models.enums.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDataDTO {
    private UUID id;
    private String email;
    private UserRole role;
    private LocalDateTime expiration;
    private TokenType tokenType;
}
