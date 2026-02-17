package br.com.imobmatch.api.infra.s3.repository;

import br.com.imobmatch.api.infra.s3.config.AwsProperties;
import br.com.imobmatch.api.infra.s3.config.S3BucketsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class S3RepositoryImpl implements S3Repository {

    private final S3Client s3Client;
    private final S3BucketsProperties buckets;
    private final AwsProperties awsProperties;


    @Override
    public String uploadProfilePhoto(UUID userId, byte[] content) {
        String key = generateKey(userId);
        upload(key, content, buckets.getProfilePhotos());
        return key;
    }

    @Override
    public String uploadPropertyImage(UUID propertyId, byte[] content) {
        String key = generateKey(propertyId);
        upload(key, content, buckets.getPropertyImages());
        return key;
    }

    @Override
    public byte[] downloadProfilePhoto(String key) {
        return this.download(key, this.buckets.getProfilePhotos());
    }

    @Override
    public byte[] downloadPropertyImage(String key) {
        return this.download(key, this.buckets.getPropertyImages());
    }

    @Override
    public void deleteProfilePhoto(String key) {
        this.delete(key, this.buckets.getProfilePhotos());
    }

    @Override
    public void deletePropertyImage(String key) {
        this.delete(key, this.buckets.getPropertyImages());
    }

    // ----------------- private helpers -----------------

    private String generateKey(UUID id) {
        return id + "-" + UUID.randomUUID();
    }


    private void upload(String key, byte[] content, String bucket) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType("image/jpeg")
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(content));
    }

    private void delete(String key, String bucket) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        s3Client.deleteObject(request);
    }

    private byte[] download(String key, String bucket) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        try {
            ResponseBytes<GetObjectResponse> response = s3Client.getObjectAsBytes(request);
            return response.asByteArray();
        } catch (SdkException e) {
            throw new RuntimeException("Error downloading from S3", e);
        }
    }


    public String buildProfilePhotoUrl(String key) {
        return buildPublicUrl(
                buckets.getProfilePhotos(),
                key
        );
    }

    private String buildPublicUrl(String bucketName, String key) {

        if (awsProperties.getS3().getEndpoint() != null) {
            return String.format("%s/%s/%s",
                    awsProperties.getS3().getEndpoint(),
                    bucketName,
                    key
            );
        }

        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName,
                awsProperties.getRegion(),
                key
        );
    }

}
