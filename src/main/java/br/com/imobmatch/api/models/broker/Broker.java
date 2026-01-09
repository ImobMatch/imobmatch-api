package br.com.imobmatch.api.models.broker;

import java.util.UUID;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "creci", nullable = false)
    private String creci;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

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
}