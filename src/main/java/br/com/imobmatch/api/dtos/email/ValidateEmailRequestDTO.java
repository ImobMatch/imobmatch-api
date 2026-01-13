package br.com.imobmatch.api.dtos.email;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ValidateEmailRequestDTO {

    private Long verificationId;

    private String code;
}

