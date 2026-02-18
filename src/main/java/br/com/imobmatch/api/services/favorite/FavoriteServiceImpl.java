package br.com.imobmatch.api.services.favorite;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.favorite.Favorite;
import br.com.imobmatch.api.models.property.Property;
import br.com.imobmatch.api.dtos.favorite.FavoriteResponseDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.exceptions.broker.BrokerNotFoundException;
import br.com.imobmatch.api.exceptions.favorite.FavoriteNotFoundException;
import br.com.imobmatch.api.exceptions.property.PropertyNotFoundException;
import br.com.imobmatch.api.mappers.PropertyMapper;
import br.com.imobmatch.api.repositories.BrokerRepository;
import br.com.imobmatch.api.repositories.FavoriteRepository;
import br.com.imobmatch.api.repositories.PropertyRepository;
import br.com.imobmatch.api.services.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    
    private final FavoriteRepository favoriteRepository;
    private final BrokerRepository brokerRepository;
    private final PropertyRepository PropertyRepository;
    private final AuthService authService;

    private final PropertyMapper propertyMapper;

    @Override
    public FavoriteResponseDTO favoriteProperty(UUID propertyId){
        Broker broker = brokerRepository.findById(authService.getMe().getId()).orElseThrow(BrokerNotFoundException::new);
        Property property = PropertyRepository.findById(propertyId).orElseThrow(PropertyNotFoundException::new);

        Favorite favorite = Favorite.builder()
            .broker(broker)
            .property(property)
            .favoritedAt(LocalDateTime.now())
        .build();

        favoriteRepository.save(favorite);
        return buildFavoriteResponseDTO(favorite);
    }

    @Override
    public void unfavoriteProperty(UUID propertyId){
        UUID brokerId = authService.getMe().getId();
        Favorite favorite = favoriteRepository.findByBroker_IdAndProperty_Id(brokerId, propertyId).orElseThrow(FavoriteNotFoundException::new);

        favoriteRepository.delete(favorite);
    }

    @Override
    public List<FavoriteResponseDTO> getAllBrokerFavorites(){
        List<Favorite> favorites = favoriteRepository.findAllByBroker_Id(authService.getMe().getId());
        return favorites.stream()
        .map(favorite -> buildFavoriteResponseDTO(favorite))
        .collect(Collectors.toList());
    }

    @Override
    public List<PropertyResponseDTO> getAllBrokerFavoriteProperties(){
        List<Favorite> favorites = favoriteRepository.findAllByBroker_Id(authService.getMe().getId());
        return favorites.stream()
        .map(favorite -> propertyMapper.toDTO(favorite.getProperty()))
        .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<UUID> getUserFavoritePropertyIds(UUID brokerId) {
        return favoriteRepository.findAllPropertyIdsByBrokerId(brokerId);
    }

    private FavoriteResponseDTO buildFavoriteResponseDTO(Favorite favorite){
        return FavoriteResponseDTO.builder()
            .id(favorite.getId())
            .brokerId(favorite.getBroker().getId())
            .propertyId(favorite.getProperty().getId())
            .favoritedAt(favorite.getFavoritedAt())
        .build();
    }
}
