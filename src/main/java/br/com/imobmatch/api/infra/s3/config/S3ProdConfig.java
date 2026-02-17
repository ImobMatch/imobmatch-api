package br.com.imobmatch.api.infra.s3.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@Profile("prod")
public class S3ProdConfig {

    @Value("${aws.region}")
    private String region;

    @Bean
    public S3Client s3Client() {

        return S3Client.builder()
                .region(Region.of(region))
                .build();
    }
}
