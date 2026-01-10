package br.com.imobmatch.api.dtos.owner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerPostDTO {

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

    private String phoneDddNumber;
    private String phoneNumber;
    private Boolean isPrimaryPhone;

}
