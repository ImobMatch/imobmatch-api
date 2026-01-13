package br.com.imobmatch.api.dtos.owner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerCreateDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    @CPF(message = "Invalid CPF")
    private String cpf;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotBlank
    private String whatsAppPhoneNumber;

    @NotBlank
    private String personalPhoneNumber;
}
