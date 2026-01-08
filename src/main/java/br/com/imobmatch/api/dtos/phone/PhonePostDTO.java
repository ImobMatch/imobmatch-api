package br.com.imobmatch.api.dtos.phone;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhonePostDTO {
    private String ddd;
    private String number;
    private boolean isPrimary;
}
