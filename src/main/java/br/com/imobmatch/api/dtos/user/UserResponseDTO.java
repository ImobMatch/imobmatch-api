package br.com.imobmatch.api.dtos.user;

import br.com.imobmatch.api.models.user.enums.UserRole;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private UUID id;
    private String email;
    private UserRole role;
}
