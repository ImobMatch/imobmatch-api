package br.com.imobmatch.api.services.broker;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.broker.BrokerPatchDTO;
import br.com.imobmatch.api.dtos.broker.BrokerPostDTO;
import br.com.imobmatch.api.dtos.broker.BrokerResponseDTO;
import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.enums.BrokerAccountStatus;
import br.com.imobmatch.api.models.enums.BrokerBusinessType;
import br.com.imobmatch.api.models.enums.BrokerPropertyType;

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

    List<BrokerResponseDTO> getBrokersByOperationCity(String operationCity);

    List<BrokerResponseDTO> getBrokersByPropertyType(BrokerPropertyType propertyType);

    List<BrokerResponseDTO> getBrokersByBusinessType(BrokerBusinessType businessType);
    
    List<BrokerResponseDTO> getAllBrokers();

    List<BrokerResponseDTO> getBrokersByAccountStatus(BrokerAccountStatus accountStatus);

    List<BrokerResponseDTO> getPendingBrokers();
    
    void approveBroker(UUID brokerId);

    void rejectBroker(UUID brokerId);

    void deleteBroker(PasswordUserDeleteDTO passwordUserDeleteDTO);

    void deleteBroker(UUID id);

    List<BrokerResponseDTO> search(String regionInterest, String operationCity, BrokerPropertyType propertyType, BrokerBusinessType businessType);
}
