package br.com.imobmatch.api.services.broker;

import java.util.List;
import java.util.UUID;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.broker.BrokerPatchDTO;
import br.com.imobmatch.api.dtos.broker.BrokerPostDTO;
import br.com.imobmatch.api.dtos.broker.BrokerResponseDTO;
import br.com.imobmatch.api.models.enums.BrokerAccountStatus;
import br.com.imobmatch.api.models.enums.PropertyBusinessType;
import br.com.imobmatch.api.models.enums.PropertyType;

public interface BrokerService {

    BrokerResponseDTO createBroker(BrokerPostDTO brokerPostDTO);

    BrokerResponseDTO updateBroker(BrokerPatchDTO brokerPatchDTO);

    BrokerResponseDTO updateBroker(UUID id, BrokerPatchDTO brokerPatchDTO);

    BrokerResponseDTO getBroker();

    BrokerResponseDTO getBrokerById(UUID id);

    BrokerResponseDTO getBrokerByEmail(String email);

    BrokerResponseDTO getBrokerByCreci(String creci);

    BrokerResponseDTO getBrokerByCpf(String cpf);

    List<BrokerResponseDTO> getBrokersByName(String name);

    List<BrokerResponseDTO> getBrokersByRegionInterest(String regionInterest);

    List<BrokerResponseDTO> getBrokersByPropertyType(PropertyType propertyType);

    List<BrokerResponseDTO> getBrokersByBusinessType(PropertyBusinessType businessType);

    List<BrokerResponseDTO> getAllBrokers();

    List<BrokerResponseDTO> getBrokersByAccountStatus(BrokerAccountStatus accountStatus);

    List<BrokerResponseDTO> getPendingBrokers();

    void approveBroker(UUID brokerId);

    void rejectBroker(UUID brokerId);

    void deleteBroker(PasswordUserDeleteDTO passwordUserDeleteDTO);

    void deleteBroker(UUID id);

    List<BrokerResponseDTO> search(String regionInterest, PropertyType propertyType, PropertyBusinessType businessType);
}
