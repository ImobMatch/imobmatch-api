package br.com.imobmatch.api.infra.s3.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "aws.s3.bucket")
public class S3BucketsProperties {
    private String profilePhotos;
    private String creciDocuments;
    private String propertyDocuments;
    private String propertyImages;
}
