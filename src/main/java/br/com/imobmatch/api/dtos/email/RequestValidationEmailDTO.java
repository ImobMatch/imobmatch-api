package br.com.imobmatch.api.dtos.email;

import jakarta.validation.constraints.Email;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestValidationEmailDTO {
    @Email
    private String email;
}
