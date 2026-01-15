package br.com.imobmatch.api.dtos.broker;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import br.com.imobmatch.api.models.enums.BrokerBusinessType;
import br.com.imobmatch.api.models.enums.BrokerPropertyType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

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

    private String regionInterest;

    private BrokerPropertyType propertyType;

    private String operationCity;

    private BrokerBusinessType businessType;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotBlank
    private String whatsAppPhoneNumber;

    private String personalPhoneNumber;
}
