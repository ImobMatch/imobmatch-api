package br.com.imobmatch.api.models.property;

import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.enums.BrazilianState;
import br.com.imobmatch.api.models.enums.PropertyType;
import br.com.imobmatch.api.models.owner.Owner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "owner_id",  nullable = false)
    private Owner ownerId;

    @ManyToOne
    @JoinColumn(name = "broker_id", nullable = true)
    private Broker broker_id;

    @OneToOne
    @JoinColumn(name = "address_id",  nullable = true)
    private Address address_id;

    @OneToOne
    @JoinColumn(name = "Characteristic_id")
    private PropertyCharacteristic propertyCharacteristic;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PropertyType type;
}
