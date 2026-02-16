package br.com.imobmatch.api.models.property;

import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.enums.PropertyBusinessType;
import br.com.imobmatch.api.models.enums.PropertyManager;
import br.com.imobmatch.api.models.enums.PropertyPurpose;
import br.com.imobmatch.api.models.enums.PropertyType;
import br.com.imobmatch.api.models.owner.Owner;
import br.com.imobmatch.api.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "sale_price")
    private BigDecimal salePrice;

    @Column(name = "rent_price")
    private BigDecimal rentPrice;

    @Column(name = "iptu_value")
    private BigDecimal iptuValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose",  nullable = false)
    private PropertyPurpose  purpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_type", nullable = false)
    private PropertyBusinessType businessType;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PropertyType type;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate =  LocalDate.now();

    @Column(name = "update_date", nullable = false)
    private LocalDate updatedDate = LocalDate.now();

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
    @Column(name = "managed_by", nullable = false)
    private PropertyManager managedBy;

    @Column(name = "owner_cpf")
    private String ownerCpf;

}
