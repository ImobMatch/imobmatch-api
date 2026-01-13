package br.com.imobmatch.api.dtos.auth;

import br.com.imobmatch.api.models.user.enums.UserRole;

import java.time.Instant;
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
    private Instant expiration;
}
