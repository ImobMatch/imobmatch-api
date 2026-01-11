package br.com.imobmatch.api.dtos.owner;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerPatchDTO {

    @NotBlank
    private String name;
}
