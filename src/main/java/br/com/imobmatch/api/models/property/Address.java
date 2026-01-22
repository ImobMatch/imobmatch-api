package br.com.imobmatch.api.models.property;

import br.com.imobmatch.api.models.enums.BrazilianState;
import br.com.imobmatch.api.models.enums.ZoneType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String street;
    private Integer number;
    private String complement;
    private String neighborhood;
    private String city;
    private BrazilianState state;
    private String zipCode;
    private String referencePoint;
    private ZoneType zoneType;
}
