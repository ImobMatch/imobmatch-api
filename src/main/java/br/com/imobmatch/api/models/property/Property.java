package br.com.imobmatch.api.models.property;

import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.enums.PropertyManager;
import br.com.imobmatch.api.models.enums.PropertyType;
import br.com.imobmatch.api.models.owner.Owner;
import br.com.imobmatch.api.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    //Resolver o problema da zona
    //Resolver a questão dos estados do pais

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private User publisher;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Characteristic_id")
    private PropertyCharacteristic characteristic;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PropertyType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "managed_by", nullable = false)
    private PropertyManager managedBy;

    @Column(name = "owner_cpf")
    private String ownerCpf;

}
