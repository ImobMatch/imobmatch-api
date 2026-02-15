package br.com.imobmatch.api.models.property;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "property_characteristic")
public class PropertyCharacteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "description")
    private String description;

    @Column(name = "area", nullable = false)
    private BigDecimal area;

    @Column(name = "num_bedrooms")
    private Short numBedrooms;

    @Column(name = "num_suites")
    private Short numSuites;

    @Column(name = "num_bathrooms")
    private Short numBathrooms;

    @Column(name = "num_garage_spaces")
    private Short numGarageSpaces;

    @Column(name = "num_living_rooms")
    private Short numLivingRooms;

    @Column(name = "num_kitchens")
    private Short numKitchens;

    @Column(name = "has_laundry")
    private Boolean hasLaundry;

    @Column(name = "has_closet")
    private Boolean hasCloset;

    @Column(name = "has_office")
    private Boolean hasOffice;

    @Column(name = "has_balcony")
    private Boolean hasBalcony;

    @Column(name = "has_terrace")
    private Boolean hasTerrace;

    @Column(name = "has_wine_cellar")
    private Boolean hasWineCellar;

    @Column(name = "has_pantry")
    private Boolean hasPantry;

    @Column(name = "has_yard")
    private Boolean hasYard;

    @Column(name = "has_garden")
    private Boolean hasGarden;

    @Column(name = "has_barbecue")
    private Boolean hasBarbecue;

    @Column(name = "has_storage")
    private Boolean hasStorage;


}
