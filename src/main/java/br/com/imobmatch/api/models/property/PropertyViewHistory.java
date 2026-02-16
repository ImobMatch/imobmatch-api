package br.com.imobmatch.api.models.property;

import br.com.imobmatch.api.models.broker.Broker;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "broker_property_view_history")
public class PropertyViewHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "broker_id")
    private Broker broker;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "viewed_at")
    private LocalDateTime viewedAt =  LocalDateTime.now();
}
