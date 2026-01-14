package br.com.imobmatch.api.dtos.owner;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerGetAllByResponseDTO {

    private List<OwnerResponseDTO> owners;
}
