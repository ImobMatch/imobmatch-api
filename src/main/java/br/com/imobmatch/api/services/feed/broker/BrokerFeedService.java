package br.com.imobmatch.api.services.feed.broker;

import br.com.imobmatch.api.dtos.broker.BrokerPreferencesDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.property.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.util.UUID;

public interface BrokerFeedService {

    @Async
    void trackView(Property property, Broker broker);
    BrokerPreferencesDTO getPreferences(Broker broker);
    Page<PropertyResponseDTO> getRecommendationsForUser(UUID userId, Pageable pageable);
}
