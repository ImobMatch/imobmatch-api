package br.com.imobmatch.api.dtos.broker;

import java.util.UUID;

import br.com.imobmatch.api.models.broker.BrokerBusinessType;
import br.com.imobmatch.api.models.broker.BrokerPropertyType;
import br.com.imobmatch.api.models.user.UserRole;
import lombok.*;

@Getter
@AllArgsConstructor
public class BrokerResponseDTO {

    private UUID id;
    private String name;
    private String creci;
    private String cpf;
    private String region_interest;
    private BrokerPropertyType propertyType;
    private String operationCity;
    private BrokerBusinessType businessType;
    private String email;
    private UserRole role;
    private boolean isEmailVerified;
}