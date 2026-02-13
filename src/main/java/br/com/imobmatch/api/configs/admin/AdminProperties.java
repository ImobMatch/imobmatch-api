package br.com.imobmatch.api.configs.admin;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "api.admin")
public class AdminProperties {

    private String email;
    private String password;
}
