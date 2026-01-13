package br.com.imobmatch.api.models.broker;

import java.util.UUID;

import br.com.imobmatch.api.models.broker.enums.BrokerBusinessType;
import br.com.imobmatch.api.models.broker.enums.BrokerPropertyType;
import br.com.imobmatch.api.models.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "brokers")
@Table(name = "brokers")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class Broker {
    
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "creci", nullable = false, unique = true)
    private String creci;

    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "region_interest", nullable = true)
    private String regionInterest;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = true)
    private BrokerPropertyType propertyType;

    @Column(name = "operation_city", nullable = true)
    private String operationCity;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_type", nullable = true)
    private BrokerBusinessType businessType;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", nullable = false, unique = true)
    private User user;
}