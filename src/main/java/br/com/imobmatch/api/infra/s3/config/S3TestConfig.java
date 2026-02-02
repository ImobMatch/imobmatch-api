package br.com.imobmatch.api.infra.s3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Profile("test")
public class S3TestConfig {

    @Bean
    public S3Client s3Client() {
        // S3Client “fake” para testes
        return new S3Client() {
            @Override
            public String serviceName() {
                return "";
            }

            @Override
            public void close() {}

            // Sobrescreva métodos que você chamar nos testes, se precisar
            // Por exemplo, getObjectAsBytes, putObject, deleteObject
            // Ou use Mockito para mockar
        };
    }
}
