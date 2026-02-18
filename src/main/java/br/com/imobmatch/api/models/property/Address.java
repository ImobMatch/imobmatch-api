package br.com.imobmatch.api.models.property;

import br.com.imobmatch.api.models.enums.BrazilianState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "number")
    private Integer number;
    @Column(name = "complement")
    private String complement;
    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;
    @Column(name = "city", nullable = false)
    private String city;
    @Enumerated(EnumType.STRING)
    @Column(name = "state",nullable = false)
    private BrazilianState state;
    @Column(name = "zip_code", nullable = false)
    private String zipCode;
    @Column(name = "latitude")
    private String latitude;
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "referencePoint")
    private String referencePoint;
}
