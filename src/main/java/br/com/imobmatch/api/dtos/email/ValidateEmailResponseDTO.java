package br.com.imobmatch.api.dtos.email;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ValidateEmailResponseDTO {

    private String email;
    private boolean verified;
}
