package br.com.imobmatch.api.infra.s3.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
@Data
public class AwsProperties {

    private String region;
    private String accessKey;
    private String secretKey;
    private S3 s3;

    @Data
    public static class S3 {
        private String endpoint;
        private String publicBaseUrl;
        private Bucket bucket;
    }

    @Data
    public static class Bucket {
        private String profilePhotos;
        private String propertyImages;
    }
}

