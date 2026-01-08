package br.com.imobmatch.api.services.user;

import br.com.imobmatch.api.dtos.phone.PhonePostDTO;
import br.com.imobmatch.api.dtos.phone.PhoneResponseDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.user.PhoneExistsException;
import br.com.imobmatch.api.exceptions.user.UserExistsException;
import br.com.imobmatch.api.exceptions.user.UserNotFoundException;
import br.com.imobmatch.api.models.phone.Phone;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.user.UserRole;
import br.com.imobmatch.api.repositories.PhoneRepository;
import br.com.imobmatch.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserResponseDTO create(String email, String password, UserRole role) {
        if(this.userRepository.findByEmail(email).isPresent()){
            throw new UserExistsException();
        }

        String encryptedPassword = this.bCryptPasswordEncoder.encode(password);
        User newUser = new User(
            email,
            encryptedPassword,
            role
        );

        this.userRepository.save(newUser);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail(newUser.getEmail());
        userResponseDTO.setId(newUser.getId());
        userResponseDTO.setRole(newUser.getRole());
        return userResponseDTO;
    }


    public UserResponseDTO getById(UUID id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if(optionalUser.isPresent()){
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setId(optionalUser.get().getId());
            userResponseDTO.setEmail(optionalUser.get().getEmail());
            userResponseDTO.setRole(optionalUser.get().getRole());
            return userResponseDTO;
        }
        throw new UserNotFoundException();
    }

    public UserResponseDTO getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        return new UserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Override
    public PhoneResponseDTO addPhone(PhonePostDTO phonePostDTO, UUID id) throws UserNotFoundException {
        User user = this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if(this.phoneRepository.findByDddAndNumber(phonePostDTO.getDdd(), phonePostDTO.getNumber()).isPresent()){
            throw new PhoneExistsException("Phone Exists");
        }

        Phone phone = new Phone();
        phone.setNumber(phonePostDTO.getNumber());
        phone.setUser(user);
        phone.setDdd(phonePostDTO.getDdd());
        phone.setPrimary(phonePostDTO.isPrimary());

        this.phoneRepository.save(phone);
        return  new PhoneResponseDTO(
                phone.getUser().getId(),
                phone.getId(),
                phone.getDdd(),
                phone.getNumber()
        );

    }

    @Override
    public User findEntityById(UUID id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return user;
    }

    @Override
    public UserResponseDTO deleteById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setRole(user.getRole());
        return userResponseDTO;
    }
}
