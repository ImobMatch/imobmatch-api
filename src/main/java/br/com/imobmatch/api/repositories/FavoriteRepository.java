package br.com.imobmatch.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.imobmatch.api.models.favorite.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, UUID>{
    
}