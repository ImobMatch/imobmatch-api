package br.com.imobmatch.api.services.broker;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.broker.BrokerPatchDTO;
import br.com.imobmatch.api.dtos.broker.BrokerPostDTO;
import br.com.imobmatch.api.dtos.broker.BrokerResponseDTO;

public interface BrokerService {
    
    BrokerResponseDTO createBroker(BrokerPostDTO brokerPostDTO);

    BrokerResponseDTO updateBroker(BrokerPatchDTO brokerPatchDTO);

    BrokerResponseDTO getBroker();

    void deleteBroker(PasswordUserDeleteDTO passwordUserDeleteDTO);
}
