package br.com.imobmatch.api.models.phone;

import br.com.imobmatch.api.models.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Phone {

    @Column(name = "ddd", nullable = false)
    private String ddd;

    @Column(name = "number", nullable = false)
    private String number;

    public String getFormatedNumber(){

        return "(" + ddd + ") " + number;
    }
}
