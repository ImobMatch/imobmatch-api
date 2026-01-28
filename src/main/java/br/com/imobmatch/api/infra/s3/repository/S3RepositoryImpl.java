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
        return key;
    }

    @Override
    public String uploadCreciDocument(UUID userId, byte[] content) {
        String key = generateKey(userId, this.buckets.getCreciDocuments());
        upload(key, content, "application/pdf", buckets.getCreciDocuments());
        return key;
    }

    @Override
    public String uploadPropertyDocument(UUID userId, byte[] content) {
        String key = generateKey(userId, this.buckets.getPropertyDocuments());
        upload(key, content, "application/pdf", buckets.getPropertyDocuments());
        return key;
    }

    @Override
    public String uploadPropertyImage(UUID userId, byte[] content) {
        String key = generateKey(userId, this.buckets.getPropertyImages());
        upload(key, content, "image/jpeg", buckets.getPropertyImages());
        return key;
    }


    @Override
    public byte[] downloadProfilePhoto(String key) {
        return this.download(key, this.buckets.getProfilePhotos());
    }

    @Override
    public byte[] downloadCreciDocument(String key) {
        return this.download(key, this.buckets.getCreciDocuments());
    }

    @Override
    public byte[] downloadPropertyDocument(String key) {
        return this.download(key, this.buckets.getPropertyDocuments());
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
    public void deleteCreciDocument(String key) {
        this.delete(key, this.buckets.getCreciDocuments());
    }

    @Override
    public void deletePropertyDocument(String key) {
        this.delete(key, this.buckets.getPropertyDocuments());
    }

    @Override
    public void deletePropertyImage(String key) {
        this.delete(key, this.buckets.getPropertyImages());
    }


    private String generateKey(UUID userId, String type) {
        return type + "/" + userId + "-" + UUID.randomUUID();
    }


    private void upload(String key, byte[] content, String contentType, String bucket) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
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
            throw new RuntimeException("Error Download in S3", e);
        }
    }


}
