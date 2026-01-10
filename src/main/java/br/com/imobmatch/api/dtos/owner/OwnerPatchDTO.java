package br.com.imobmatch.api.dtos.owner;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerPatchDTO {

    private String name;

    @CPF
    private String cpf;
}
