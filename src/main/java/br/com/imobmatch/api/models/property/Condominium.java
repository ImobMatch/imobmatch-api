package br.com.imobmatch.api.models.property;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Condominium {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "has_gym")
    private Boolean hasGym;

    @Column(name = "has_pool")
    private Boolean hasPool;

    @Column(name = "has_sauna")
    private Boolean hasSauna;

    @Column(name = "has_spa")
    private Boolean hasSpa;

    @Column(name = "has_party_room")
    private Boolean hasPartyRoom;

    @Column(name = "has_sports_courts")
    private Boolean hasSportsCourts;

    @Column(name = "has_playground")
    private Boolean hasPlayground;

    @Column(name = "has_coworking_space")
    private Boolean hasCoworkingSpace;

    @Column(name = "has_cinema")
    private Boolean hasCinema;

    @Column(name = "has_game_room")
    private Boolean hasGameRoom;

    @Column(name = "has_shared_terrace")
    private Boolean hasSharedTerrace;

    @Column(name = "has_mini_market")
    private Boolean hasMiniMarket;

    @Column(name = "has_pet_area")
    private Boolean hasPetArea;

    @Column(name = "has_bike_storage")
    private Boolean hasBikeStorage;

    @Column(name = "has_restaurant")
    private Boolean hasRestaurant;

    @Column(name = "has_24h_security")
    private Boolean has24hSecurity;

    @Column(name = "has_cameras")
    private Boolean hasCameras;

    @Column(name = "has_elevators")
    private Boolean hasElevators;

    @Column(name = "has_electric_car_station")
    private Boolean hasElectricCarStation;

}
