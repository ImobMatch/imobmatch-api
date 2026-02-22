package br.com.imobmatch.api.dtos.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedResponseDTO {

    private UUID id;
    private String title;
    private String image;
    private String location;
    private LocalDate updatedAt;
    private BigDecimal sale;
    private BigDecimal rent;
    private BigDecimal size;
    private Short bedrooms;
    private Short suites;
    private Short bathrooms;
    private Short garages;
    private Boolean isFavorite;
    private List<PropertyImageDTO> images;
}