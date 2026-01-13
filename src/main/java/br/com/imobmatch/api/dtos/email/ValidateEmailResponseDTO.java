package br.com.imobmatch.api.dtos.email;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ValidateEmailResponseDTO {

    private String email;
    private boolean verified;
}
