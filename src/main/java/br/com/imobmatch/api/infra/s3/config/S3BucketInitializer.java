package br.com.imobmatch.api.infra.s3.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
@Profile({"dev", "prod"})
public class S3BucketInitializer {

    private final S3Client s3Client;
    private final S3BucketsProperties buckets;
    private final boolean isProd;

    public S3BucketInitializer(
            S3Client s3Client,
            S3BucketsProperties buckets,
            org.springframework.core.env.Environment env
    ) {
        this.s3Client = s3Client;
        this.buckets = buckets;
        this.isProd = env.acceptsProfiles("prod");
    }

    @PostConstruct
    public void initializeStorageBuckets() {

        setupBucket(buckets.getProfilePhotos());
        setupBucket(buckets.getPropertyImages());
    }

    private void setupBucket(String bucket) {

        createIfNotExists(bucket);

        // 🔥 Só executa em produção
        if (isProd) {
            disablePublicAccessBlock(bucket);
            makeBucketPublic(bucket);
        }
    }

    private void createIfNotExists(String bucket) {
        try {
            s3Client.headBucket(b -> b.bucket(bucket));
        } catch (Exception e) {
            s3Client.createBucket(b -> b.bucket(bucket));
        }
    }

    private void disablePublicAccessBlock(String bucket) {

        s3Client.putPublicAccessBlock(
                PutPublicAccessBlockRequest.builder()
                        .bucket(bucket)
                        .publicAccessBlockConfiguration(
                                PublicAccessBlockConfiguration.builder()
                                        .blockPublicAcls(false)
                                        .ignorePublicAcls(false)
                                        .blockPublicPolicy(false)
                                        .restrictPublicBuckets(false)
                                        .build()
                        )
                        .build()
        );
    }

    private void makeBucketPublic(String bucket) {
        s3Client.putBucketPolicy(
                PutBucketPolicyRequest.builder()
                        .bucket(bucket)
                        .policy(loadPolicy(bucket))
                        .build()
        );
    }

    private String loadPolicy(String bucket) {
        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("s3/public-read-policy.json")) {

            if (is == null) {
                throw new RuntimeException("Policy não encontrada");
            }

            String template = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return template.formatted(bucket);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar policy", e);
        }
    }
}
