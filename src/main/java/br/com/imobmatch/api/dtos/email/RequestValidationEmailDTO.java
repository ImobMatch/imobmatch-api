package br.com.imobmatch.api.dtos.email;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RequestValidationEmailDTO {
    @Email
    private String email;
}
