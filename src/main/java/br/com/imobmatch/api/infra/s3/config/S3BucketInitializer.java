package br.com.imobmatch.api.infra.s3.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;

@Component
@Profile({"dev", "prod"})
public class S3BucketInitializer {

    private final S3Client s3Client;
    private final S3BucketsProperties buckets;

    public S3BucketInitializer(S3Client s3Client, S3BucketsProperties buckets) {
        this.s3Client = s3Client;
        this.buckets = buckets;
    }

    @PostConstruct
    public void initializeStorageBuckets() {
        createIfNotExists(buckets.getProfilePhotos());
        createIfNotExists(buckets.getCreciDocuments());
        createIfNotExists(buckets.getPropertyDocuments());
        createIfNotExists(buckets.getPropertyImages());
    }

    private void createIfNotExists(String bucket) {
        try {
            s3Client.headBucket(b -> b.bucket(bucket));
        } catch (Exception e) {
            s3Client.createBucket(b -> b.bucket(bucket));
        }
    }
}
