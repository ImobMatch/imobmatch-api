package br.com.imobmatch.api.dtos.property.condominium;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CondominiumUpdateDTO {

    @NotBlank(message = "name is required")
    private String name;
    private BigDecimal price;
}
