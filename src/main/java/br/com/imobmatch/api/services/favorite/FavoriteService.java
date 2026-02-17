package br.com.imobmatch.api.services.favorite;

import java.util.List;
import java.util.UUID;

import br.com.imobmatch.api.dtos.favorite.FavoriteResponseDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;

public interface FavoriteService {

    public FavoriteResponseDTO favoriteProperty(UUID propertyID);
    public void unfavoriteProperty(UUID propertyID);
    public List<FavoriteResponseDTO> getAllBrokerFavorites();
    public List<PropertyResponseDTO> getAllBrokerFavoriteProperties();
}
