package br.com.imobmatch.api.models.favorite;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.property.Property;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "favorites")
@Table(name = "favorites",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"broker_id", "property_id"})})
public class Favorite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "broker_id")
    private Broker broker;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    private LocalDateTime favoritedAt;
}