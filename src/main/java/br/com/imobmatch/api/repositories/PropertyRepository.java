package br.com.imobmatch.api.repositories;

import br.com.imobmatch.api.models.enums.BrazilianState;
import br.com.imobmatch.api.models.enums.PropertyBusinessType;
import br.com.imobmatch.api.models.enums.PropertyPurpose;
import br.com.imobmatch.api.models.enums.PropertyType;
import br.com.imobmatch.api.models.property.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface PropertyRepository extends JpaRepository<Property, UUID>,
                                            JpaSpecificationExecutor<Property> {

    @Query("""
    SELECT p FROM Property p
    JOIN p.characteristic c
    WHERE p.isAvailable = true
    AND (p.publisher.id != :currentUserId OR p.publisher IS NULL)
    AND p.address.state IN :regions

    ORDER BY (
        (CASE  WHEN :businessType IS NOT NULL AND p.businessType = :businessType OR p.businessType = 'SALE_AND_RENT' THEN 40 ELSE 0 END) +
        
        (CASE WHEN p.publicationDate >= :recentDateThreshold THEN 30 ELSE 0 END) +
        
        (CASE WHEN p.publicationDate >= :superRecentDateThreshold THEN 50 ELSE 0 END) +
        
        (CASE
            WHEN :purpose IS NOT NULL AND p.purpose = :purpose THEN 20
            ELSE 0
        END) +
        
        (CASE WHEN p.type = :favType THEN 15 ELSE 0 END) +

        (CASE
            WHEN :avgPrice IS NOT NULL AND p.salePrice <= (:avgPrice * 1.50) THEN 30
            WHEN :avgPrice IS NULL THEN 10
            ELSE 0
         END) +

        (10 - ABS(COALESCE(c.numBedrooms, 0) - COALESCE(:avgBedrooms, 0))) +

        (10 - ABS(COALESCE(c.numSuites, 0) - COALESCE(:avgSuites, 0))) +

        (10 - ABS(COALESCE(c.numGarageSpaces, 0) - COALESCE(:avgGarage, 0))) +
       
        (5 - ABS(COALESCE(c.numBathrooms, 0) - COALESCE(:avgBathrooms, 0))) +
        
        (CASE WHEN c.hasBarbecue = true THEN 10 ELSE 0 END) +
        (CASE WHEN c.hasGarden = true THEN 10 ELSE 0 END) +
        (CASE WHEN c.hasBalcony = true THEN 10 ELSE 0 END) +
        (CASE WHEN c.hasOffice = true THEN 10 ELSE 0 END) +
        (CASE WHEN c.hasCloset = true THEN 10 ELSE 0 END) +
        (CASE WHEN c.hasPantry = true THEN 10 ELSE 0 END) +
        (CASE WHEN c.hasWineCellar = true THEN 10 ELSE 0 END) +
        (CASE WHEN c.hasStorage = true THEN 10 ELSE 0 END) +
        (CASE WHEN c.hasLaundry = true THEN 10 ELSE 0 END)

    ) DESC
""")
    Page<Property> findRecommendations(
            @Param("purpose") PropertyPurpose purpose,
            @Param("favType") PropertyType favType,
            @Param("avgPrice") BigDecimal avgPrice,
            @Param("avgBedrooms") Double avgBedrooms,
            @Param("avgSuites") Double avgSuites,
            @Param("avgGarage") Double avgGarage,
            @Param("avgBathrooms") Double avgBathrooms,
            @Param("businessType") PropertyBusinessType businessType,
            @Param("recentDateThreshold") LocalDate recentDateThreshold,
            @Param("superRecentDateThreshold") LocalDate superRecentDateThreshold,
            @Param("currentUserId") UUID currentUserId,
            @Param("regions") Set<BrazilianState> regions,
            Pageable pageable
    );

}
