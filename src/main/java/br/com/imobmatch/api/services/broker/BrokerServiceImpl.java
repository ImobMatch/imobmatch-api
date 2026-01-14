package br.com.imobmatch.api.services.broker;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.broker.*;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.auth.AuthenticationException;
import br.com.imobmatch.api.exceptions.broker.BrokerExistsException;
import br.com.imobmatch.api.exceptions.broker.BrokerNotFoundException;
import br.com.imobmatch.api.exceptions.broker.BrokerNoValidDataProvideException;
import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.user.enums.UserRole;
import br.com.imobmatch.api.repositories.BrokerRepository;
import br.com.imobmatch.api.services.auth.AuthService;
import br.com.imobmatch.api.services.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BrokerServiceImpl implements BrokerService {

    private BrokerRepository brokerRepository;
    private UserService userService;
    private AuthService authService;

    @Override
    @Transactional
    public BrokerResponseDTO createBroker(BrokerPostDTO brokerPostDTO) {

        if(brokerRepository.existsBrokerByCpf(brokerPostDTO.getCpf()) || brokerRepository.existsBrokerByCreci(brokerPostDTO.getCreci())) {
            throw new BrokerExistsException();}
        UserResponseDTO userResponseDTO = userService.create(
            brokerPostDTO.getEmail(),
            brokerPostDTO.getPassword(),
            UserRole.BROKER
        );

        User user = userService.findEntityById(userResponseDTO.getId());
        Broker broker = new Broker();
        broker.setId(user.getId());
        broker.setName(brokerPostDTO.getName());
        broker.setCreci(brokerPostDTO.getCreci());
        broker.setCpf(brokerPostDTO.getCpf());
        broker.setRegionInterest(brokerPostDTO.getRegionInterest());
        broker.setPropertyType(brokerPostDTO.getPropertyType());
        broker.setOperationCity(brokerPostDTO.getOperationCity());
        broker.setBusinessType(brokerPostDTO.getBusinessType());
        broker.setUser(user);

        brokerRepository.save(broker);
        return new BrokerResponseDTO(
            broker.getId(),
            broker.getName(),
            broker.getCreci(),
            broker.getCpf(),
            broker.getRegionInterest(),
            broker.getPropertyType(),
            broker.getOperationCity(),
            broker.getBusinessType(),
            user.getEmail(),
            user.getRole(),
            user.isEmailVerified()
        );
    }

    @Override
    public BrokerResponseDTO updateBroker(BrokerPatchDTO brokerPatchDTO) {
        Broker broker = brokerRepository.findById(authService.getMe().getId())
            .orElseThrow(BrokerNotFoundException::new);

        boolean isUpdated = false;
        if(brokerPatchDTO.getName() != null && !brokerPatchDTO.getName().isBlank()) {
            broker.setName(brokerPatchDTO.getName());
            isUpdated = true;
        }

        if(brokerPatchDTO.getRegionInterest() != null && !brokerPatchDTO.getRegionInterest().isBlank()) {
            broker.setRegionInterest(brokerPatchDTO.getRegionInterest());
            isUpdated = true;
        }

        if(brokerPatchDTO.getOperationCity() != null && !brokerPatchDTO.getOperationCity().isBlank()) {
            broker.setOperationCity(brokerPatchDTO.getOperationCity());
            isUpdated = true;
        }

        if(brokerPatchDTO.getPropertyType() != null && !brokerPatchDTO.getPropertyType().toString().isBlank()) {
            broker.setPropertyType(brokerPatchDTO.getPropertyType());
            isUpdated = true;
        }

        if(brokerPatchDTO.getBusinessType() != null && !brokerPatchDTO.getBusinessType().toString().isBlank()) {
            broker.setBusinessType(brokerPatchDTO.getBusinessType());
            isUpdated = true;
        }

        if(!isUpdated) {throw new BrokerNoValidDataProvideException();}

        brokerRepository.save(broker);
        return new BrokerResponseDTO(
            broker.getId(),
            broker.getName(),
            broker.getCreci(),
            broker.getCpf(),
            broker.getRegionInterest(),
            broker.getPropertyType(),
            broker.getOperationCity(),
            broker.getBusinessType(),
            broker.getUser().getEmail(),
            broker.getUser().getRole(),
            broker.getUser().isEmailVerified()
        );
    }

    @Override
    public BrokerResponseDTO getBroker() {
        Broker broker = brokerRepository.findById(authService.getMe().getId())
            .orElseThrow(BrokerNotFoundException::new);
        
        return new BrokerResponseDTO(
            broker.getId(),
            broker.getName(),
            broker.getCreci(),
            broker.getCpf(),
            broker.getRegionInterest(),
            broker.getPropertyType(),
            broker.getOperationCity(),
            broker.getBusinessType(),
            broker.getUser().getEmail(),
            broker.getUser().getRole(),
            broker.getUser().isEmailVerified()
        );
    }


    @Override
    @Transactional
    public void deleteBroker(PasswordUserDeleteDTO passwordUserDeleteDTO)throws AuthenticationException
            , BrokerNotFoundException {
        
        UUID userId = authService.getMe().getId();
        brokerRepository.deleteById(userId);
        userService.deleteById(userId, passwordUserDeleteDTO.getPassword());
    }
    
}
