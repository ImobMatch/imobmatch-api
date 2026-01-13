package br.com.imobmatch.api.dtos.owner;

import br.com.imobmatch.api.models.user.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
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

