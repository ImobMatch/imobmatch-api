package br.com.imobmatch.api.models.property;

import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.enums.BrokerBusinessType;
import br.com.imobmatch.api.models.enums.PropertyManager;
import br.com.imobmatch.api.models.enums.PropertyType;
import br.com.imobmatch.api.models.owner.Owner;
import br.com.imobmatch.api.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private User publisher;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "characteristic_id", nullable = false)
    private PropertyCharacteristic characteristic;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "condominium_id")
    private Condominium condominium;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "business_type") 
    private BrokerBusinessType businessType;

    @Column(name = "sale_value")
    private BigDecimal saleValue;

    @Column(name = "rental_value")
    private BigDecimal rentalValue;

}
