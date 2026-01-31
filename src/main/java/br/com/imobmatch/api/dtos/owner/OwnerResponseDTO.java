package br.com.imobmatch.api.dtos.owner;

import br.com.imobmatch.api.models.enums.UserRole;
import java.time.LocalDate;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerResponseDTO {

    private UUID id;
    private String name;
    private String cpf;
    private String email;
    private UserRole role;
    private String whatsAppPhoneNumber;
    private String personalPhoneNumber;
    private LocalDate birthDate;
    private boolean isEmailVerified;
}

