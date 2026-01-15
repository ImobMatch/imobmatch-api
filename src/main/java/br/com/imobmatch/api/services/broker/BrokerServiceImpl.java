package br.com.imobmatch.api.services.broker;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
import lombok.RequiredArgsConstructor;

    @Service
    @RequiredArgsConstructor 
    public class BrokerServiceImpl implements BrokerService {

        // private final AuthController authController;
        private final BrokerValidationService brokerValidationService; 
        private final BrokerRepository brokerRepository;
        private final UserService userService;
        private final AuthService authService;
    
    

    @Override
    @Transactional
    public BrokerResponseDTO createBroker(BrokerPostDTO brokerPostDTO) {

        brokerValidationService.run(brokerPostDTO);
        String cleanCpf = removeNonDigits(brokerPostDTO.getCpf()); 
        String cleanCreci = getCleanCreci(brokerPostDTO.getCreci());
        String cleanWhatsAppPhoneNumber = removeNonDigits(brokerPostDTO.getWhatsAppPhoneNumber());
        String cleanPersonalPhoneNumber = brokerPostDTO.getPersonalPhoneNumber() != null ? 
            removeNonDigits(brokerPostDTO.getPersonalPhoneNumber()) : null;

        if(brokerRepository.existsBrokerByCpf(cleanCpf) || 
        brokerRepository.existsBrokerByCreci(cleanCreci) ||
        brokerRepository.existsByUser_Email(brokerPostDTO.getEmail())) {
            throw new BrokerExistsException();}

        UserResponseDTO userResponseDTO = userService.create(
            brokerPostDTO.getEmail(),
            brokerPostDTO.getPassword(),
            UserRole.BROKER
        );

        User user = userService.findEntityById(userResponseDTO.getId());
        Broker broker = new Broker();
        broker.setId(user.getId());
        broker.setName(brokerPostDTO.getName().strip());
        broker.setCreci(cleanCreci);
        broker.setCpf(cleanCpf);
        broker.setRegionInterest(brokerPostDTO.getRegionInterest().strip());
        broker.setPropertyType(brokerPostDTO.getPropertyType());
        broker.setOperationCity(brokerPostDTO.getOperationCity().strip());
        broker.setBusinessType(brokerPostDTO.getBusinessType());
        broker.setUser(user);
        broker.setBirthDate(brokerPostDTO.getBirthDate());
        broker.setWhatsAppPhoneNumber(cleanWhatsAppPhoneNumber);
        broker.setPersonalPhoneNumber(cleanPersonalPhoneNumber);
        broker.setAccountStatus(BrokerAccountStatus.PENDING);

        brokerRepository.save(broker);
        return buildBrokerResponseDto(broker);
    }

    @Override
    public BrokerResponseDTO updateBroker(BrokerPatchDTO brokerPatchDTO) {
        Broker broker = brokerRepository.findById(authService.getMe().getId())
            .orElseThrow(BrokerNotFoundException::new);

        boolean isUpdated = false;
        if(brokerPatchDTO.getName() != null && !brokerPatchDTO.getName().isBlank()) {
            broker.setName(brokerPatchDTO.getName().strip());
            isUpdated = true;
        }

        if(brokerPatchDTO.getRegionInterest() != null && !brokerPatchDTO.getRegionInterest().isBlank()) {
            broker.setRegionInterest(brokerPatchDTO.getRegionInterest().strip());
            isUpdated = true;
        }

        if(brokerPatchDTO.getOperationCity() != null && !brokerPatchDTO.getOperationCity().isBlank()) {
            broker.setOperationCity(brokerPatchDTO.getOperationCity().strip());
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
        if(brokerPatchDTO.getBirthDate() != null) {
            broker.setBirthDate(brokerPatchDTO.getBirthDate());
            isUpdated = true;
        }

        if(brokerPatchDTO.getWhatsAppPhoneNumber() != null && !brokerPatchDTO.getWhatsAppPhoneNumber().isBlank()) {
            broker.setWhatsAppPhoneNumber(removeNonDigits(brokerPatchDTO.getWhatsAppPhoneNumber()));
            isUpdated = true;
        }

        if(brokerPatchDTO.getPersonalPhoneNumber() != null && !brokerPatchDTO.getPersonalPhoneNumber().isBlank()) {
            broker.setPersonalPhoneNumber(removeNonDigits(brokerPatchDTO.getPersonalPhoneNumber()));
            isUpdated = true;
        }

        if(!isUpdated) {throw new BrokerNoValidDataProvideException();}

        brokerRepository.save(broker);
        return buildBrokerResponseDto(broker);
    }

    @Override
    public BrokerResponseDTO getMeBroker() {
        Broker broker = brokerRepository.findById(authService.getMe().getId())
            .orElseThrow(BrokerNotFoundException::new);
        
        return buildBrokerResponseDto(broker);
    }

    @Override
    public BrokerResponseDTO getByIdBroker(UUID id) {
        Broker broker = brokerRepository.findById(id)
            .orElseThrow(BrokerNotFoundException::new);
        
        return buildBrokerResponseDto(broker);
    }

    @Override
    public BrokerResponseDTO getByEmailBroker(String email) {
        Broker broker = brokerRepository.findByUser_Email(email)
            .orElseThrow(BrokerNotFoundException::new);
        
        return buildBrokerResponseDto(broker);
    }
    @Override
    public BrokerResponseDTO getByCreciBroker(String creci) {
        Broker broker = brokerRepository.findByCreci(getCleanCreci(creci))
            .orElseThrow(BrokerNotFoundException::new);
        
        return buildBrokerResponseDto(broker);
    }

    @Override
    public BrokerResponseDTO getByCpfBroker(String cpf) {
        Broker broker = brokerRepository.findByCpf(removeNonDigits(cpf))
            .orElseThrow(BrokerNotFoundException::new);
        
        return buildBrokerResponseDto(broker);
    }

    @Override
    public List<BrokerResponseDTO> ListByNameBroker(String name) {
        List<Broker> brokers = brokerRepository.findByNameContainingIgnoreCase(name.strip());
        return brokers.stream()
        .map(broker -> buildBrokerResponseDto(broker))
        .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> ListByRegionInterestBroker(String regionInterest) {
        List<Broker> brokers = brokerRepository.findByRegionInterestContainingIgnoreCase(regionInterest.strip());
        return brokers.stream()
        .map(broker -> buildBrokerResponseDto(broker))
        .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> ListByOperationCityBroker(String operationCity) {
        List<Broker> brokers = brokerRepository.findByOperationCityContainingIgnoreCase(operationCity.strip());
        return brokers.stream()
        .map(broker -> buildBrokerResponseDto(broker))
        .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> ListByPropertyTypeBroker(BrokerPropertyType propertyType) {
        List<Broker> brokers = brokerRepository.findByPropertyType(propertyType);
        return brokers.stream()
        .map(broker -> buildBrokerResponseDto(broker))
        .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> ListByBusinessTypeBroker(BrokerBusinessType businessType) {
        List<Broker> brokers = brokerRepository.findByBusinessType(businessType);
        return brokers.stream()
        .map(broker -> buildBrokerResponseDto(broker))
        .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> ListAllBroker() {
        List<Broker> brokers = brokerRepository.findAll();
        return brokers.stream()
        .map(broker -> buildBrokerResponseDto(broker))
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

    private BrokerResponseDTO buildBrokerResponseDto(Broker broker){

        return new BrokerResponseDTO(
            broker.getId(),
            broker.getName(),
            broker.getCreci(),
            broker.getCpf(),
            broker.getRegionInterest(),
            broker.getPropertyType(),
            broker.getOperationCity(),
            broker.getBusinessType(),
            broker.getBirthDate(),
            broker.getWhatsAppPhoneNumber(),
            broker.getPersonalPhoneNumber(),
            broker.getUser().getEmail(),
            broker.getUser().getRole(),
            broker.getUser().isEmailVerified(),
            broker.getAccountStatus()
        );
    }

    private String getCleanCreci(String creci) {
        return creci.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
    }

    private String removeNonDigits(String cpf) {
        return cpf.replaceAll("\\D", "");
    }
}
