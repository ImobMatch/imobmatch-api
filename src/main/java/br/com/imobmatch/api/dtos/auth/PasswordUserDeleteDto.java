package br.com.imobmatch.api.dtos.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@Getter
@Setter
public class PasswordUserDeleteDto {

    @NotNull
    @NotEmpty
    @Length(min = 8)
    private String password;
}
