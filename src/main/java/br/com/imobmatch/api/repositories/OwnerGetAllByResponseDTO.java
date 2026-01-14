package br.com.imobmatch.api.repositories;

import br.com.imobmatch.api.dtos.owner.OwnerResponseDTO;
import br.com.imobmatch.api.models.owner.Owner;
import lombok.*;

import java.util.List;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerGetAllByResponseDTO {

    private List<OwnerResponseDTO> owners;
}
