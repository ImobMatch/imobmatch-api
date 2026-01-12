package br.com.imobmatch.api.dtos.owner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerUpdateDTO {

    private String name;
    private String phoneNumber;
    private String phoneDdd;


}
