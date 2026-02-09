package br.com.imobmatch.api.configs;

import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.enums.UserRole;
import br.com.imobmatch.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*
    * Classe criada especialmente para inicializar dados no banco de dados ao iniciar a aplicação.
    * Neste caso, cria um usuário administrador padrão se ele não existir.
     */

    @Override
    public void run(String... args) {
        String adminEmail = "admin@imobmatch.com";

        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("admin123")); // Senha padrão aqui. Não sei se é uma boa prática.
            admin.setRole(UserRole.ADMIN);
            admin.setEmailVerified(true);
            
            userRepository.save(admin);
            System.out.println("USUÁRIO ADMIN CRIADO: " + adminEmail + " / admin123");
        }
    }
}