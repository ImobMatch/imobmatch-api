package br.com.imobmatch.api.configs.secret;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class JwtSecretConfig {

    @Value("${api.security.token.secret:}")
    private String secret;

    @Bean
    @Qualifier("jwtSecret")
    @Profile("test")
    public String testJwtSecret() {
        return java.util.UUID.randomUUID().toString();
    }

    @Bean
    @Qualifier("jwtSecret")
    @Profile({"dev","prod"})
    public String devProdJwtSecret() {
        return secret;
    }
}
