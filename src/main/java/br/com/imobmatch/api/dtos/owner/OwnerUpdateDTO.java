package br.com.imobmatch.api.dtos.owner;

import jakarta.validation.constraints.Past;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnerUpdateDTO {

    private String name;
    private String whatsAppPhoneNumber;
    private String personalPhoneNumber;
    @Past
    private LocalDate birthDate;

}
