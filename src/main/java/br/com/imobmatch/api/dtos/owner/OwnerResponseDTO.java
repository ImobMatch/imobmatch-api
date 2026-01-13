package br.com.imobmatch.api.dtos.owner;

import br.com.imobmatch.api.models.enums.UserRole;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class OwnerResponseDTO {

    private UUID id;
    private String name;
    private String cpf;
    private String email;
    private UserRole role;
    private String phoneNumber;
    private String phoneDdd;
    private LocalDate birthDate;
    private boolean isEmailVerified;
}

