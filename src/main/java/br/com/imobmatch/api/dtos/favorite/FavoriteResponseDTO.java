package br.com.imobmatch.api.dtos.favorite;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class FavoriteResponseDTO {

    private UUID id;
    private UUID brokerId;
    private UUID propertyId;
    private LocalDateTime favoritedAt;
}
