package br.com.imobmatch.api.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.imobmatch.api.models.favorite.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID>{

    List<Favorite> findAllByBroker_Id(UUID brokerId);
    Optional<Favorite> findByBroker_IdAndProperty_Id(UUID brokerId, UUID propertyId);
}