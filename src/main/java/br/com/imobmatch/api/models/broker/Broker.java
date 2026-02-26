package br.com.imobmatch.api.models.broker;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import br.com.imobmatch.api.models.enums.BrazilianState;
import br.com.imobmatch.api.models.enums.BrokerAccountStatus;
import br.com.imobmatch.api.models.enums.PropertyBusinessType;
import br.com.imobmatch.api.models.enums.PropertyType;
import br.com.imobmatch.api.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

@Entity(name = "brokers")
@Table(name = "brokers")
@Builder
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

    @ElementCollection
    @CollectionTable(
        name = "brokers_regions_interest",
        joinColumns = @JoinColumn(name = "broker_id")
    )
    @Column(name = "region", nullable = true)
    private Set<BrazilianState> regionsInterest;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @CollectionTable(
        name = "brokers_property_types",
        joinColumns = @JoinColumn(name = "broker_id")
    )
    @Column(name = "property_type", nullable = true)
    private Set<PropertyType> propertyTypes;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "business_type", nullable = true)
    private PropertyBusinessType businessType;

    @Past
    @Column(name ="birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name="whats_app_phone_number",  nullable = false, length = 20)
    private String whatsAppPhoneNumber;

    @Column(name="personal_phone_number", length = 20)
    private String personalPhoneNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "account_status", nullable = false)
    private BrokerAccountStatus accountStatus;
}