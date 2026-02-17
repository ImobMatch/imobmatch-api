package br.com.imobmatch.api.infra.s3.repository;

import java.util.UUID;

public interface S3Repository {

    // Uploads
    String uploadProfilePhoto(UUID userId, byte[] content);
    String uploadPropertyImage(UUID userId, byte[] content);

    byte[] downloadProfilePhoto(String key);
    byte[] downloadPropertyImage(String key);

    // Deletes
    void deleteProfilePhoto(String key);
    void deletePropertyImage(String key);
}
