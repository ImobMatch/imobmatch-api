package br.com.imobmatch.api.dtos.owner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerPatchDto {

    private String name;

    @CPF
    private String cpf;

}
