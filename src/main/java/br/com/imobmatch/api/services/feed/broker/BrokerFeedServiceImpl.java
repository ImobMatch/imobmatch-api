package br.com.imobmatch.api.services.feed.broker;

import br.com.imobmatch.api.dtos.broker.BrokerPreferencesDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.exceptions.broker.BrokerNotFoundException;
import br.com.imobmatch.api.mappers.PropertyMapper;
import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.enums.BrazilianState;
import br.com.imobmatch.api.models.enums.PropertyBusinessType;
import br.com.imobmatch.api.models.enums.PropertyPurpose;
import br.com.imobmatch.api.models.enums.PropertyType;
import br.com.imobmatch.api.models.property.Property;
import br.com.imobmatch.api.models.property.PropertyViewHistory;
import br.com.imobmatch.api.repositories.BrokerRepository;
import br.com.imobmatch.api.repositories.PropertyRepository;
import br.com.imobmatch.api.repositories.PropertyViewHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BrokerFeedServiceImpl implements BrokerFeedService {

    private final PropertyViewHistoryRepository repository;
    private final BrokerRepository brokerRepository;
    private final PropertyRepository propertyRepository;
    private final PropertyMapper mapper;


    @Override
    public void trackView(Property property, Broker broker) {

        PropertyViewHistory view = new PropertyViewHistory();
        view.setProperty(property);
        view.setBroker(broker);
        repository.save(view);

    }

    /**
     * Build a dto based in average data obtained from "clicks" in properties
     * @param broker Broker object for find
     * @return A DTO which average preferences
     */
    @Override
    public BrokerPreferencesDTO getPreferences(Broker broker) {

        List<PropertyViewHistory> history = repository.findTop20ByBrokerOrderByViewedAtDesc(broker);

        if (history.isEmpty()) return null;

        return BrokerPreferencesDTO.builder()
                .avgBedrooms(calculateAvg(history, p -> p.getCharacteristic().getNumBedrooms()))
                .avgSuites(calculateAvg(history, p -> p.getCharacteristic().getNumSuites()))
                .avgBathrooms(calculateAvg(history, p -> p.getCharacteristic().getNumBathrooms()))
                .avgGarage(calculateAvg(history, p -> p.getCharacteristic().getNumGarageSpaces()))
                .avgPrice(calculateAvgPrice(history))
                .favoriteType(calculateMode(history, Property::getType))
                .businessType(calculateMode(history, Property::getBusinessType))
                .propertyPurpose(calculateMode(history, Property::getPurpose))
                .build();
    }

    @Override
    public Page<PropertyResponseDTO> getRecommendationsForUser(UUID userId, Pageable pageable) {
        //Use default values for fist login
        Broker user = brokerRepository.findById(userId).orElseThrow(BrokerNotFoundException :: new);
        BrokerPreferencesDTO profile = getPreferences(user);

        PropertyBusinessType businessType = profile != null ? profile.getBusinessType() : PropertyBusinessType.SALE_AND_RENT;
        PropertyPurpose purpose = profile != null ? profile.getPropertyPurpose() : PropertyPurpose.RESIDENTIAL;
        PropertyType type = profile != null ? profile.getFavoriteType() : PropertyType.APARTMENT;
        BigDecimal price = profile != null ? profile.getAvgPrice() : new BigDecimal("500000");

        //prioritize recents
        LocalDate twoWeeksAgo = LocalDate.now().minusDays(14);
        LocalDate threeDaysAgo = LocalDate.now().minusDays(3);

        Double bedrooms = profile != null ? profile.getAvgBedrooms() : 2.0;
        Double suites = profile != null ? profile.getAvgSuites() : 1.0;
        Double garage = profile != null ? profile.getAvgGarage() : 1.0;
        Double bathrooms = profile != null ? profile.getAvgBathrooms() : 2.0;

        Set<BrazilianState> regionsOfInterest = user.getRegionsInterest();

        if (regionsOfInterest == null || regionsOfInterest.isEmpty()) {
            regionsOfInterest = Set.of(BrazilianState.values());
        }

        Page<Property> recommendations = propertyRepository
                .findRecommendations(
                purpose,
                type,
                price,
                bedrooms,
                suites,
                garage,
                bathrooms,
                businessType,
                twoWeeksAgo,
                threeDaysAgo,
                userId,
                regionsOfInterest,
                pageable
        );
        return recommendations.map(mapper::toDTO);
    }

    private Double calculateAvg(List<PropertyViewHistory> list, Function<Property, Short> extractor) {
        return list.stream()
                .map(h -> extractor.apply(h.getProperty()))
                .filter(Objects::nonNull)
                .mapToInt(Short::intValue)
                .average()
                .orElse(0.0);
    }

    private BigDecimal calculateAvgPrice(List<PropertyViewHistory> list) {
        return list.stream()
                .map(h -> h.getProperty().getSalePrice())
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(Math.max(1, list.size())), RoundingMode.HALF_UP);
    }

    private <T> T calculateMode(List<PropertyViewHistory> list, Function<Property, T> extractor) {
        return list.stream()
                .map(h -> extractor.apply(h.getProperty()))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

}
