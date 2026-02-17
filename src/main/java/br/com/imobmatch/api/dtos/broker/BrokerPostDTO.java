package br.com.imobmatch.api.dtos.broker;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrokerPostDTO {
    
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String creci;

    @NotBlank
    @CPF(message = "Invalid CPF")
    private String cpf;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotBlank
    private String whatsAppPhoneNumber;
}
