package br.com.imobmatch.api.services.broker;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import br.com.imobmatch.api.controllers.AuthController;
import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.broker.*;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.auth.AuthenticationException;
import br.com.imobmatch.api.exceptions.broker.BrokerExistsException;
import br.com.imobmatch.api.exceptions.broker.BrokerNotFoundException;
import br.com.imobmatch.api.exceptions.broker.BrokerNoValidDataProvideException;
import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.enums.BrokerAccountStatus;
import br.com.imobmatch.api.models.enums.BrokerBusinessType;
import br.com.imobmatch.api.models.enums.BrokerPropertyType;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.enums.UserRole;
import br.com.imobmatch.api.repositories.BrokerRepository;
import br.com.imobmatch.api.services.auth.AuthService;
import br.com.imobmatch.api.services.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BrokerServiceImpl implements BrokerService {

    private final AuthController authController;

    private BrokerRepository brokerRepository;
    private UserService userService;
    private AuthService authService;

    BrokerServiceImpl(AuthController authController) {
        this.authController = authController;
    }

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
        broker.setAccountStatus(BrokerAccountStatus.PENDING);

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
            user.isEmailVerified(),
            broker.getAccountStatus()
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
            broker.getUser().isEmailVerified(),
            broker.getAccountStatus()
        );
    }

    @Override
    public BrokerResponseDTO getMeBroker() {
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
            broker.getUser().isEmailVerified(),
            broker.getAccountStatus()
        );
    }

    @Override
    public BrokerResponseDTO getByIdBroker(UUID id) {
        Broker broker = brokerRepository.findById(id)
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
            broker.getUser().isEmailVerified(),
            broker.getAccountStatus()
        );
    }
/*
    @Override
    public BrokerResponseDTO getByEmailBroker(String email) {
        Broker broker = brokerRepository.findByEmail(email)
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
*/
    @Override
    public BrokerResponseDTO getByCreciBroker(String creci) {
        Broker broker = brokerRepository.findByCreci(creci)
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
            broker.getUser().isEmailVerified(),
            broker.getAccountStatus()
        );
    }

    @Override
    public BrokerResponseDTO getByCpfBroker(String cpf) {
        Broker broker = brokerRepository.findByCpf(cpf)
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
            broker.getUser().isEmailVerified(),
            broker.getAccountStatus()
        );
    }

    @Override
    public List<BrokerResponseDTO> ListByNameBroker(String name) {
        List<Broker> brokers = brokerRepository.findByNameContainingIgnoreCase(name);
        return brokers.stream()
        .map(broker -> new BrokerResponseDTO(
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
            broker.getUser().isEmailVerified(),
            broker.getAccountStatus()
        ))
        .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> ListByRegionInterestBroker(String regionInterest) {
        List<Broker> brokers = brokerRepository.findByRegionInterestContainingIgnoreCase(regionInterest);
        return brokers.stream()
        .map(broker -> new BrokerResponseDTO(
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
            broker.getUser().isEmailVerified(),
            broker.getAccountStatus()
        ))
        .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> ListByOperationCityBroker(String operationCity) {
        List<Broker> brokers = brokerRepository.findByOperationCityContainingIgnoreCase(operationCity);
        return brokers.stream()
        .map(broker -> new BrokerResponseDTO(
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
            broker.getUser().isEmailVerified(),
            broker.getAccountStatus()
        ))
        .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> ListByPropertyTypeBroker(BrokerPropertyType propertyType) {
        List<Broker> brokers = brokerRepository.findByPropertyType(propertyType);
        return brokers.stream()
        .map(broker -> new BrokerResponseDTO(
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
            broker.getUser().isEmailVerified(),
            broker.getAccountStatus()
        ))
        .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> ListByBusinessTypeBroker(BrokerBusinessType businessType) {
        List<Broker> brokers = brokerRepository.findByBusinessType(businessType);
        return brokers.stream()
        .map(broker -> new BrokerResponseDTO(
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
            broker.getUser().isEmailVerified(),
            broker.getAccountStatus()
        ))
        .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> ListAllBroker() {
        List<Broker> brokers = brokerRepository.findAll();
        return brokers.stream()
        .map(broker -> new BrokerResponseDTO(
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
            broker.getUser().isEmailVerified(),
            broker.getAccountStatus()
        ))
        .collect(Collectors.toList());
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
