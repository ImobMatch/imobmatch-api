package br.com.imobmatch.api.dtos.property.condominium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CondominiumResponseDTO {

    private UUID id;
    private String name;
    private BigDecimal price;
}
