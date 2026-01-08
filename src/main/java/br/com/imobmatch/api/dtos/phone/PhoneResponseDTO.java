package br.com.imobmatch.api.dtos.phone;

import java.util.UUID;

public record PhoneResponseDTO(UUID userID, UUID phoneID, String ddd, String number) {
}
