package br.com.imobmatch.api.infra.s3.repository;

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

    @Override
    public String uploadProfilePhoto(UUID userId, byte[] content) {
        String key = generateKey(userId, buckets.getProfilePhotos());
        upload(key, content, "image/jpeg", buckets.getProfilePhotos());
        return buildPublicUrl(buckets.getProfilePhotos(), key);
    }

    @Override
    public String uploadPropertyImage(UUID propertyId, byte[] content) {
        String key = generateKey(propertyId, buckets.getPropertyImages());
        upload(key, content, "image/jpeg", buckets.getPropertyImages());
        return buildPublicUrl(buckets.getPropertyImages(), key);
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

    private String generateKey(UUID id, String folder) {
        return folder + "/" + id + "-" + UUID.randomUUID();
    }

    private void upload(String key, byte[] content, String contentType, String bucket) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .acl(ObjectCannedACL.PUBLIC_READ) // ← deixa o objeto público
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

    private String buildPublicUrl(String bucketName, String key) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, key);
    }
}
