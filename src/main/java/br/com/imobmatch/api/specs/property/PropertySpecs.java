package br.com.imobmatch.api.specs.property;

import br.com.imobmatch.api.dtos.property.PropertyFilterDTO;
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

            addGe(predicates, builder, root.get("salePrice"), filter.getMinSalePrice());
            addLe(predicates, builder, root.get("salePrice"), filter.getMaxSalePrice());
            addGe(predicates, builder, root.get("rentPrice"), filter.getMinRentPrice());
            addLe(predicates, builder, root.get("rentPrice"), filter.getMaxRentPrice());;
            addGe(predicates, builder, root.get("iptuValue"), filter.getMinIptuValue());
            addLe(predicates, builder, root.get("iptuValue"), filter.getMaxIptuValue());

            addLike(predicates, builder, root.get("title"), filter.getTitle());
            addEqual(predicates, builder, root.get("type"), filter.getType());
            addEqual(predicates, builder, root.get("managedBy"), filter.getManagedBy());
            addEqual(predicates, builder, root.get("isAvailable"), filter.getIsAvailable());
            addEqual(predicates, builder, root.get("ownerCpf"), filter.getOwnerCpf());
            addEqual(predicates, builder, root.get("purpose"), filter.getPurpose());
            addEqual(predicates, builder, root.get("businessType"), filter.getBusinessType());

            if (filter.getPublisherId() != null) {
                predicates.add(builder.equal(root.get("publisher").get("id"), filter.getPublisherId()));
            }

            if (hasCharacteristicFilters(filter)) {
                Join<Property, PropertyCharacteristic> charJoin = root.join("characteristic", JoinType.INNER);

                addGe(predicates, builder, charJoin.get("area"), filter.getMinArea());
                addLe(predicates, builder, charJoin.get("area"), filter.getMaxArea());

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
                addLe(predicates, builder, condoJoin.get("price"), filter.getMaxCondoPrice());
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
        return StringUtils.hasText(f.getCondominiumName()) || f.getMaxCondoPrice() != null;
    }
}