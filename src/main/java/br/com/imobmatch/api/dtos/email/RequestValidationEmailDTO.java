package br.com.imobmatch.api.dtos.email;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestValidationEmailDTO {
    private String email;
}
