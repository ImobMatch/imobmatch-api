package br.com.imobmatch.api.dtos.password;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StatusPasswordResetDTO {
    private String email;
    private boolean swapPassword;
}
