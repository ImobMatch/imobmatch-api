package br.com.imobmatch.api.services.broker;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.broker.*;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.auth.AuthenticationException;
import br.com.imobmatch.api.exceptions.broker.BrokerExistsException;
import br.com.imobmatch.api.exceptions.broker.BrokerNotFoundException;
import br.com.imobmatch.api.exceptions.broker.BrokerNoValidDataProvideException;
import br.com.imobmatch.api.models.broker.Broker;
import br.com.imobmatch.api.models.enums.BrokerAccountStatus;
import br.com.imobmatch.api.models.enums.PropertyBusinessType;
import br.com.imobmatch.api.models.enums.PropertyType;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.enums.UserRole;
import br.com.imobmatch.api.repositories.BrokerRepository;
import br.com.imobmatch.api.services.auth.AuthService;
import br.com.imobmatch.api.services.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrokerServiceImpl implements BrokerService {

    private final BrokerRepository brokerRepository;
    private final UserService userService;
    private final AuthService authService;

    @Override
    @Transactional
    public BrokerResponseDTO createBroker(BrokerPostDTO brokerPostDTO) {

        if (!isCreciValid(brokerPostDTO.getCreci())) {
            throw new IllegalArgumentException("Invalid CRECI. Ex: 12345PB");
        }

        String cleanCpf = removeNonDigits(brokerPostDTO.getCpf());
        String cleanCreci = getCleanCreci(brokerPostDTO.getCreci());
        String cleanWhatsAppPhoneNumber = removeNonDigits(brokerPostDTO.getWhatsAppPhoneNumber());

        if (brokerRepository.existsBrokerByCpf(cleanCpf) ||
                brokerRepository.existsBrokerByCreci(cleanCreci) ||
                brokerRepository.existsByUser_Email(brokerPostDTO.getEmail())) {
            throw new BrokerExistsException();
        }

        UserResponseDTO userResponseDTO = userService.create(
                brokerPostDTO.getEmail(),
                brokerPostDTO.getPassword(),
                UserRole.BROKER);

        User user = userService.findEntityById(userResponseDTO.getId());
        Broker broker = Broker.builder()
                .name(brokerPostDTO.getName().strip())
                .creci(cleanCreci)
                .cpf(cleanCpf)
                .user(user)
                .birthDate(brokerPostDTO.getBirthDate())
                .whatsAppPhoneNumber(cleanWhatsAppPhoneNumber)
                .accountStatus(BrokerAccountStatus.PENDING)
                .build();

        brokerRepository.save(broker);
        return buildBrokerResponseDto(broker);
    }

    @Override
    public BrokerResponseDTO updateBroker(BrokerPatchDTO brokerPatchDTO) {
        Broker broker = brokerRepository.findById(authService.getMe().getId())
                .orElseThrow(BrokerNotFoundException::new);

        boolean isUpdated = false;
        if (brokerPatchDTO.getName() != null && !brokerPatchDTO.getName().isBlank()) {
            broker.setName(brokerPatchDTO.getName().strip());
            isUpdated = true;
        }

        if (brokerPatchDTO.getRegionsInterest() != null) {
            broker.setRegionsInterest(brokerPatchDTO.getRegionsInterest());
            isUpdated = true;
        }

        if (brokerPatchDTO.getPropertyTypes() != null) {
            broker.setPropertyTypes(brokerPatchDTO.getPropertyTypes());
            isUpdated = true;
        }

        if (brokerPatchDTO.getBusinessType() != null) {
            broker.setBusinessType(brokerPatchDTO.getBusinessType());
            isUpdated = true;
        }

        if (brokerPatchDTO.getBirthDate() != null) {
            broker.setBirthDate(brokerPatchDTO.getBirthDate());
            isUpdated = true;
        }

        if (brokerPatchDTO.getWhatsAppPhoneNumber() != null && !brokerPatchDTO.getWhatsAppPhoneNumber().isBlank()) {
            broker.setWhatsAppPhoneNumber(removeNonDigits(brokerPatchDTO.getWhatsAppPhoneNumber()));
            isUpdated = true;
        }

        if (brokerPatchDTO.getPersonalPhoneNumber() != null && !brokerPatchDTO.getPersonalPhoneNumber().isBlank()) {
            broker.setPersonalPhoneNumber(removeNonDigits(brokerPatchDTO.getPersonalPhoneNumber()));
            isUpdated = true;
        }

        if (!isUpdated) {
            throw new BrokerNoValidDataProvideException();
        }

        brokerRepository.save(broker);
        return buildBrokerResponseDto(broker);
    }

    // ADMIN ONLY
    @Override
    public BrokerResponseDTO updateBroker(UUID id, BrokerPatchDTO brokerPatchDTO) {
        Broker broker = brokerRepository.findById(id)
                .orElseThrow(BrokerNotFoundException::new);

        boolean isUpdated = false;
        if (brokerPatchDTO.getName() != null && !brokerPatchDTO.getName().isBlank()) {
            broker.setName(brokerPatchDTO.getName().strip());
            isUpdated = true;
        }

        if (brokerPatchDTO.getRegionsInterest() != null) {
            broker.setRegionsInterest(brokerPatchDTO.getRegionsInterest());
            isUpdated = true;
        }

        if (brokerPatchDTO.getPropertyTypes() != null) {
            broker.setPropertyTypes(brokerPatchDTO.getPropertyTypes());
            isUpdated = true;
        }

        if (brokerPatchDTO.getBusinessType() != null) {
            broker.setBusinessType(brokerPatchDTO.getBusinessType());
            isUpdated = true;
        }

        if (brokerPatchDTO.getBirthDate() != null) {
            broker.setBirthDate(brokerPatchDTO.getBirthDate());
            isUpdated = true;
        }

        if (brokerPatchDTO.getWhatsAppPhoneNumber() != null && !brokerPatchDTO.getWhatsAppPhoneNumber().isBlank()) {
            broker.setWhatsAppPhoneNumber(removeNonDigits(brokerPatchDTO.getWhatsAppPhoneNumber()));
            isUpdated = true;
        }

        if (brokerPatchDTO.getPersonalPhoneNumber() != null && !brokerPatchDTO.getPersonalPhoneNumber().isBlank()) {
            broker.setPersonalPhoneNumber(removeNonDigits(brokerPatchDTO.getPersonalPhoneNumber()));
            isUpdated = true;
        }

        if (!isUpdated) {
            throw new BrokerNoValidDataProvideException();
        }

        brokerRepository.save(broker);
        return buildBrokerResponseDto(broker);
    }

    @Override
    public BrokerResponseDTO getBroker() {
        Broker broker = brokerRepository.findById(authService.getMe().getId())
                .orElseThrow(BrokerNotFoundException::new);

        return buildBrokerResponseDto(broker);
    }

    @Override
    public BrokerResponseDTO getBrokerById(UUID id) {
        if (id == null) {
            throw new BrokerNoValidDataProvideException();
        }

        Broker broker = brokerRepository.findById(id)
                .orElseThrow(BrokerNotFoundException::new);

        return buildBrokerResponseDto(broker);
    }

    @Override
    public BrokerResponseDTO getBrokerByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new BrokerNoValidDataProvideException();
        }

        Broker broker = brokerRepository.findByUser_Email(email)
                .orElseThrow(BrokerNotFoundException::new);

        return buildBrokerResponseDto(broker);
    }

    @Override
    public BrokerResponseDTO getBrokerByCreci(String creci) {
        if (creci == null || creci.isBlank()) {
            throw new BrokerNoValidDataProvideException();
        }

        Broker broker = brokerRepository.findByCreci(getCleanCreci(creci))
                .orElseThrow(BrokerNotFoundException::new);

        return buildBrokerResponseDto(broker);
    }

    @Override
    public BrokerResponseDTO getBrokerByCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new BrokerNoValidDataProvideException();
        }

        Broker broker = brokerRepository.findByCpf(removeNonDigits(cpf))
                .orElseThrow(BrokerNotFoundException::new);

        return buildBrokerResponseDto(broker);
    }

    @Override
    public List<BrokerResponseDTO> getBrokersByName(String name) {
        if (name == null || name.isBlank()) {
            throw new BrokerNoValidDataProvideException();
        }

        List<Broker> brokers = brokerRepository.findByNameContainingIgnoreCase(name.strip());
        return brokers.stream()
                .map(broker -> buildBrokerResponseDto(broker))
                .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> getBrokersByRegionInterest(String regionInterest) {
        if (regionInterest == null || regionInterest.isBlank()) {
            throw new BrokerNoValidDataProvideException();
        }

        List<Broker> brokers = brokerRepository.findByRegionInterest(regionInterest.strip());
        return brokers.stream()
                .map(broker -> buildBrokerResponseDto(broker))
                .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> getBrokersByPropertyType(PropertyType propertyType) {
        if (propertyType == null) {
            throw new BrokerNoValidDataProvideException();
        }

        List<Broker> brokers = brokerRepository.findByPropertyType(propertyType);
        return brokers.stream()
                .map(broker -> buildBrokerResponseDto(broker))
                .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> getBrokersByBusinessType(PropertyBusinessType businessType) {
        if (businessType == null) {
            throw new BrokerNoValidDataProvideException();
        }

        List<Broker> brokers = brokerRepository.findByBusinessType(businessType);
        return brokers.stream()
                .map(broker -> buildBrokerResponseDto(broker))
                .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> getAllBrokers() {
        List<Broker> brokers = brokerRepository.findAll();
        return brokers.stream()
                .map(broker -> buildBrokerResponseDto(broker))
                .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> getBrokersByAccountStatus(BrokerAccountStatus accountStatus) {
        if (accountStatus == null) {
            throw new BrokerNoValidDataProvideException();
        }

        List<Broker> brokers = brokerRepository.findByAccountStatus(accountStatus);
        return brokers.stream()
                .map(broker -> buildBrokerResponseDto(broker))
                .collect(Collectors.toList());
    }

    @Override
    public List<BrokerResponseDTO> getPendingBrokers() {

        return brokerRepository.findByAccountStatus(BrokerAccountStatus.PENDING).stream()
                .map(this::buildBrokerResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void approveBroker(UUID brokerId) {
        Broker broker = brokerRepository.findById(brokerId)
                .orElseThrow(BrokerNotFoundException::new);

        if (broker.getAccountStatus() != BrokerAccountStatus.PENDING) {
            throw new IllegalStateException("Este corretor não está pendente de aprovação.");
        }

        broker.setAccountStatus(BrokerAccountStatus.ACTIVE);
        this.brokerRepository.save(broker);
    }

    @Override
    @Transactional
    public void rejectBroker(UUID brokerId) {
        Broker broker = brokerRepository.findById(brokerId)
                .orElseThrow(BrokerNotFoundException::new);
        if (broker.getAccountStatus() != BrokerAccountStatus.PENDING) {
            throw new IllegalStateException("Este corretor não está pendente de aprovação.");
        }

        broker.setAccountStatus(BrokerAccountStatus.REJECTED);
        brokerRepository.save(broker);

    }

    @Override
    @Transactional
    public void deleteBroker(PasswordUserDeleteDTO passwordUserDeleteDTO)
            throws AuthenticationException, BrokerNotFoundException {

        UUID userId = authService.getMe().getId();
        brokerRepository.deleteById(userId);
        userService.deleteById(userId, passwordUserDeleteDTO.getPassword());
    }

    @Override
    @Transactional
    public void deleteBroker(UUID id) {
        Broker broker = brokerRepository.findById(id)
                .orElseThrow(BrokerNotFoundException::new);
        String password = broker.getUser().getPassword();
        brokerRepository.deleteById(id);
        userService.deleteById(id, password);
    }

    @Override
    public List<BrokerResponseDTO> search(
            String regionInterest,
            PropertyType propertyType,
            PropertyBusinessType businessType) {
        List<Broker> brokers = brokerRepository.findAll();

        return brokers.stream()
                .filter(b -> regionInterest == null || regionInterest.isBlank()
                        || (b.getRegionsInterest() != null &&
                                b.getRegionsInterest().contains(regionInterest)))
                .filter(b -> propertyType == null || b.getPropertyTypes().contains(propertyType))
                .filter(b -> businessType == null || b.getBusinessType() == businessType)
                .map(this::buildBrokerResponseDto)
                .collect(Collectors.toList());
    }

    private BrokerResponseDTO buildBrokerResponseDto(Broker broker) {

        return BrokerResponseDTO.builder()
                .id(broker.getId())
                .name(broker.getName())
                .creci(broker.getCreci())
                .cpf(broker.getCpf())
                .regionsInterest(broker.getRegionsInterest())
                .propertyTypes(broker.getPropertyTypes())
                .businessType(broker.getBusinessType())
                .birthDate(broker.getBirthDate())
                .whatsAppPhoneNumber(broker.getWhatsAppPhoneNumber())
                .personalPhoneNumber(broker.getPersonalPhoneNumber())
                .email(broker.getUser().getEmail())
                .role(broker.getUser().getRole())
                .profileKey(broker.getUser().getProfileKey())
                .isEmailVerified(broker.getUser().isEmailVerified())
                .accountStatus(broker.getAccountStatus())
                .build();
    }

    private String getCleanCreci(String creci) {
        return creci.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
    }

    private String removeNonDigits(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    private boolean isCreciValid(String creci) {
        if (creci == null)
            return false;

        String cleanCreci = creci.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();

        return cleanCreci.matches("^\\d{4,6}[A-Z]{2}$");
    }
}
