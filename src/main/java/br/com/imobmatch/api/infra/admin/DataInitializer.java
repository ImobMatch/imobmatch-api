package br.com.imobmatch.api.infra.admin;

import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.enums.UserRole;
import br.com.imobmatch.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@RequiredArgsConstructor
@Configuration
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;

    @Override
    public void run(String... args) {

        if (!userRepository.existsByEmail(adminProperties.getEmail())) {

            User admin = new User();
            admin.setEmail(adminProperties.getEmail());
            admin.setPassword(
                    passwordEncoder.encode(adminProperties.getPassword())
            );
            admin.setRole(UserRole.ADMIN);
            admin.setEmailVerified(true);

            userRepository.save(admin);
        }
    }
}
