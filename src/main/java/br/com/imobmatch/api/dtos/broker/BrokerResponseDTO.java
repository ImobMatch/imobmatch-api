package br.com.imobmatch.api.dtos.broker;

import java.time.LocalDate;
import java.util.UUID;

import br.com.imobmatch.api.models.enums.BrokerAccountStatus;
import br.com.imobmatch.api.models.enums.BrokerBusinessType;
import br.com.imobmatch.api.models.enums.BrokerPropertyType;
import br.com.imobmatch.api.models.enums.UserRole;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
public class BrokerResponseDTO {

    private UUID id;
    private String name;
    private String creci;
    private String cpf;
    private String profileKey;
    private String regionInterest;
    private BrokerPropertyType propertyType;
    private String operationCity;
    private BrokerBusinessType businessType;
    private LocalDate birthDate;
    private String whatsAppPhoneNumber;
    private String personalPhoneNumber;
    private String email;
    private UserRole role;
    private boolean isEmailVerified;
    private BrokerAccountStatus accountStatus;
}