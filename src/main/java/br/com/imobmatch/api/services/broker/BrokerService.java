package br.com.imobmatch.api.services.broker;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.broker.BrokerPatchDTO;
import br.com.imobmatch.api.dtos.broker.BrokerPostDTO;
import br.com.imobmatch.api.dtos.broker.BrokerResponseDTO;
import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.enums.BrokerBusinessType;
import br.com.imobmatch.api.models.enums.BrokerPropertyType;

public interface BrokerService {
    
    BrokerResponseDTO createBroker(BrokerPostDTO brokerPostDTO);

    BrokerResponseDTO updateBroker(BrokerPatchDTO brokerPatchDTO);

    BrokerResponseDTO getMeBroker();

    BrokerResponseDTO getByIdBroker(UUID id);

    BrokerResponseDTO getByEmailBroker(String email);

    BrokerResponseDTO getByCreciBroker(String creci);

    BrokerResponseDTO getByCpfBroker(String cpf);

    List<BrokerResponseDTO> ListByNameBroker(String name);

    List<BrokerResponseDTO> ListByRegionInterestBroker(String regionInterest);

    List<BrokerResponseDTO> ListByOperationCityBroker(String operationCity);

    List<BrokerResponseDTO> ListByPropertyTypeBroker(BrokerPropertyType propertyType);

    List<BrokerResponseDTO> ListByBusinessTypeBroker(BrokerBusinessType businessType);
    
    List<BrokerResponseDTO> ListAllBroker();

    void deleteBroker(PasswordUserDeleteDTO passwordUserDeleteDTO);
}
