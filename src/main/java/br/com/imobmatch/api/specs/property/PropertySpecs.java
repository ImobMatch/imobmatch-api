package br.com.imobmatch.api.specs.property;

import br.com.imobmatch.api.dtos.property.PropertyFilterDTO;
import br.com.imobmatch.api.models.property.Address; // <--- Importante
import br.com.imobmatch.api.models.property.Condominium;
import br.com.imobmatch.api.models.property.Property;
import br.com.imobmatch.api.models.property.PropertyCharacteristic;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PropertySpecs {

    public static Specification<Property> usingFilter(PropertyFilterDTO filter) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            addEqual(predicates, builder, root.get("type"), filter.getType());
            addEqual(predicates, builder, root.get("managedBy"), filter.getManagedBy());
            addEqual(predicates, builder, root.get("isAvailable"), filter.getIsAvailable());
            addEqual(predicates, builder, root.get("ownerCpf"), filter.getOwnerCpf());

            if (filter.getPublisherId() != null) {
                predicates.add(builder.equal(root.get("publisher").get("id"), filter.getPublisherId()));
            }

            if (hasCharacteristicFilters(filter)) {
                Join<Property, PropertyCharacteristic> charJoin = root.join("characteristic", JoinType.INNER);

                addGe(predicates, builder, charJoin.get("area"), filter.getMinArea());
                addLe(predicates, builder, charJoin.get("area"), filter.getMaxArea());

                addGe(predicates, builder, charJoin.get("landArea"), filter.getMinLandArea());
                addLe(predicates, builder, charJoin.get("landArea"), filter.getMaxLandArea());

                addGe(predicates, builder, charJoin.get("usableArea"), filter.getMinUsableArea());
                addLe(predicates, builder, charJoin.get("usableArea"), filter.getMaxUsableArea());

                addGe(predicates, builder, charJoin.get("totalArea"), filter.getMinTotalArea());
                addLe(predicates, builder, charJoin.get("totalArea"), filter.getMaxTotalArea());

                addGe(predicates, builder, charJoin.get("numBedrooms"), filter.getMinBedrooms());
                addGe(predicates, builder, charJoin.get("numSuites"), filter.getMinSuites());
                addGe(predicates, builder, charJoin.get("numBathrooms"), filter.getMinBathrooms());
                addGe(predicates, builder, charJoin.get("numGarageSpaces"), filter.getMinGarageSpaces());
                addGe(predicates, builder, charJoin.get("numLivingRooms"), filter.getMinLivingRooms());
                addGe(predicates, builder, charJoin.get("numKitchens"), filter.getMinKitchens());

                addIfTrue(predicates, builder, charJoin.get("hasLaundry"), filter.getHasLaundry());
                addIfTrue(predicates, builder, charJoin.get("hasCloset"), filter.getHasCloset());
                addIfTrue(predicates, builder, charJoin.get("hasOffice"), filter.getHasOffice());
                addIfTrue(predicates, builder, charJoin.get("hasBalcony"), filter.getHasBalcony());
                addIfTrue(predicates, builder, charJoin.get("hasTerrace"), filter.getHasTerrace());
                addIfTrue(predicates, builder, charJoin.get("hasWineCellar"), filter.getHasWineCellar());
                addIfTrue(predicates, builder, charJoin.get("hasPantry"), filter.getHasPantry());
                addIfTrue(predicates, builder, charJoin.get("hasYard"), filter.getHasYard());
                addIfTrue(predicates, builder, charJoin.get("hasGarden"), filter.getHasGarden());
                addIfTrue(predicates, builder, charJoin.get("hasBarbecue"), filter.getHasBarbecue());
                addIfTrue(predicates, builder, charJoin.get("hasStorage"), filter.getHasStorage());
            }

            if (hasCondominiumFilters(filter)) {
                Join<Property, Condominium> condoJoin = root.join("condominium", JoinType.INNER);

                addLike(predicates, builder, condoJoin.get("name"), filter.getCondominiumName());
                addEqual(predicates, builder, condoJoin.get("cnpj"), filter.getCondominiumCnpj());
                addLe(predicates, builder, condoJoin.get("price"), filter.getMaxCondoPrice());

                addIfTrue(predicates, builder, condoJoin.get("hasGym"), filter.getCondoHasGym());
                addIfTrue(predicates, builder, condoJoin.get("hasPool"), filter.getCondoHasPool());
                addIfTrue(predicates, builder, condoJoin.get("hasSauna"), filter.getCondoHasSauna());
                addIfTrue(predicates, builder, condoJoin.get("hasSpa"), filter.getCondoHasSpa());
                addIfTrue(predicates, builder, condoJoin.get("hasPartyRoom"), filter.getCondoHasPartyRoom());
                addIfTrue(predicates, builder, condoJoin.get("hasSportsCourts"), filter.getCondoHasSportsCourts());
                addIfTrue(predicates, builder, condoJoin.get("hasPlayground"), filter.getCondoHasPlayground());
                addIfTrue(predicates, builder, condoJoin.get("hasCoworkingSpace"), filter.getCondoHasCoworkingSpace());
                addIfTrue(predicates, builder, condoJoin.get("hasCinema"), filter.getCondoHasCinema());
                addIfTrue(predicates, builder, condoJoin.get("hasGameRoom"), filter.getCondoHasGameRoom());
                addIfTrue(predicates, builder, condoJoin.get("hasSharedTerrace"), filter.getCondoHasSharedTerrace());
                addIfTrue(predicates, builder, condoJoin.get("hasMiniMarket"), filter.getCondoHasMiniMarket());
                addIfTrue(predicates, builder, condoJoin.get("hasPetArea"), filter.getCondoHasPetArea());
                addIfTrue(predicates, builder, condoJoin.get("hasBikeStorage"), filter.getCondoHasBikeStorage());
                addIfTrue(predicates, builder, condoJoin.get("hasRestaurant"), filter.getCondoHasRestaurant());
                addIfTrue(predicates, builder, condoJoin.get("has24hSecurity"), filter.getCondoHas24hSecurity());
                addIfTrue(predicates, builder, condoJoin.get("hasCameras"), filter.getCondoHasCameras());
                addIfTrue(predicates, builder, condoJoin.get("hasElevators"), filter.getCondoHasElevators());
                addIfTrue(predicates, builder, condoJoin.get("hasElectricCarStation"), filter.getCondoHasElectricCarStation());
            }

            if (hasAddressFilters(filter)) {
                Join<Property, Address> addressJoin = root.join("address", JoinType.INNER);

                addLike(predicates, builder, addressJoin.get("street"), filter.getStreet());
                addLike(predicates, builder, addressJoin.get("neighborhood"), filter.getNeighborhood());
                addLike(predicates, builder, addressJoin.get("city"), filter.getCity());
                addLike(predicates, builder, addressJoin.get("complement"), filter.getComplement());
                addLike(predicates, builder, addressJoin.get("referencePoint"), filter.getReferencePoint());

                addLike(predicates, builder, addressJoin.get("zipCode"), filter.getZipCode());

                addEqual(predicates, builder, addressJoin.get("number"), filter.getNumber());
                addEqual(predicates, builder, addressJoin.get("state"), filter.getState());
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addEqual(List<Predicate> preds, CriteriaBuilder cb, Path<?> path, Object value) {
        if (value != null) preds.add(cb.equal(path, value));
    }

    private static void addLike(List<Predicate> preds, CriteriaBuilder cb, Path<String> path, String value) {
        if (StringUtils.hasText(value)) preds.add(cb.like(cb.upper(path), "%" + value.toUpperCase() + "%"));
    }

    private static void addGe(List<Predicate> preds, CriteriaBuilder cb, Path<? extends Number> path, Number value) {
        if (value != null) preds.add(cb.ge(path, value));
    }

    private static void addLe(List<Predicate> preds, CriteriaBuilder cb, Path<? extends Number> path, Number value) {
        if (value != null) preds.add(cb.le(path, value));
    }

    private static void addIfTrue(List<Predicate> preds, CriteriaBuilder cb, Path<Boolean> path, Boolean value) {
        if (Boolean.TRUE.equals(value)) preds.add(cb.isTrue(path));
    }

    private static boolean hasCharacteristicFilters(PropertyFilterDTO f) {
        return f.getMinArea() != null || f.getMaxArea() != null ||
                f.getMinLandArea() != null || f.getMaxLandArea() != null ||
                f.getMinUsableArea() != null || f.getMaxUsableArea() != null ||
                f.getMinTotalArea() != null || f.getMaxTotalArea() != null ||
                f.getMinBedrooms() != null || f.getMinSuites() != null ||
                f.getMinBathrooms() != null || f.getMinGarageSpaces() != null ||
                f.getMinLivingRooms() != null || f.getMinKitchens() != null ||
                Boolean.TRUE.equals(f.getHasLaundry()) || Boolean.TRUE.equals(f.getHasCloset()) ||
                Boolean.TRUE.equals(f.getHasOffice()) || Boolean.TRUE.equals(f.getHasBalcony()) ||
                Boolean.TRUE.equals(f.getHasTerrace()) || Boolean.TRUE.equals(f.getHasWineCellar()) ||
                Boolean.TRUE.equals(f.getHasPantry()) || Boolean.TRUE.equals(f.getHasYard()) ||
                Boolean.TRUE.equals(f.getHasGarden()) || Boolean.TRUE.equals(f.getHasBarbecue()) ||
                Boolean.TRUE.equals(f.getHasStorage());
    }

    private static boolean hasCondominiumFilters(PropertyFilterDTO f) {
        return StringUtils.hasText(f.getCondominiumName()) || f.getMaxCondoPrice() != null ||
                StringUtils.hasText(f.getCondominiumCnpj()) ||
                Boolean.TRUE.equals(f.getCondoHasGym()) || Boolean.TRUE.equals(f.getCondoHasPool()) ||
                Boolean.TRUE.equals(f.getCondoHasSauna()) || Boolean.TRUE.equals(f.getCondoHasSpa()) ||
                Boolean.TRUE.equals(f.getCondoHasPartyRoom()) || Boolean.TRUE.equals(f.getCondoHasSportsCourts()) ||
                Boolean.TRUE.equals(f.getCondoHasPlayground()) || Boolean.TRUE.equals(f.getCondoHasCoworkingSpace()) ||
                Boolean.TRUE.equals(f.getCondoHasCinema()) || Boolean.TRUE.equals(f.getCondoHasGameRoom()) ||
                Boolean.TRUE.equals(f.getCondoHasSharedTerrace()) || Boolean.TRUE.equals(f.getCondoHasMiniMarket()) ||
                Boolean.TRUE.equals(f.getCondoHasPetArea()) || Boolean.TRUE.equals(f.getCondoHasBikeStorage()) ||
                Boolean.TRUE.equals(f.getCondoHasRestaurant()) || Boolean.TRUE.equals(f.getCondoHas24hSecurity()) ||
                Boolean.TRUE.equals(f.getCondoHasCameras()) || Boolean.TRUE.equals(f.getCondoHasElevators()) ||
                Boolean.TRUE.equals(f.getCondoHasElectricCarStation());
    }

    private static boolean hasAddressFilters(PropertyFilterDTO f) {
        return StringUtils.hasText(f.getStreet()) || f.getNumber() != null ||
                StringUtils.hasText(f.getComplement()) || StringUtils.hasText(f.getNeighborhood()) ||
                StringUtils.hasText(f.getCity()) || f.getState() != null ||
                StringUtils.hasText(f.getZipCode()) || StringUtils.hasText(f.getReferencePoint());
    }
}