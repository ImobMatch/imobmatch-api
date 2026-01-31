package br.com.imobmatch.api.dtos.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ValidateEmailRequestDTO {

    private UUID verificationId;

    private String code;
}

