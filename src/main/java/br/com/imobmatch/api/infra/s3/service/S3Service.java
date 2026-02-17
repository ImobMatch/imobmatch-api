package br.com.imobmatch.api.infra.s3.service;

import java.util.UUID;

public interface S3Service {

    // Uploads
    String uploadProfilePhoto(UUID userId, byte[] content);
    String uploadPropertyImage(UUID propertyId, byte[] content);

    // Downloads
    byte[] downloadProfilePhoto(String key);
    byte[] downloadPropertyImage(String key);

    // Deletes
    void deleteProfilePhoto(String key);
    void deletePropertyImage(String key);
}
