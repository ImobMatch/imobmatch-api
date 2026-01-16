package br.com.imobmatch.api.infra.s3.service;

import java.util.UUID;

public interface S3Service {

    // Uploads
    String uploadProfilePhoto(UUID userId, byte[] content);
    String uploadCreciDocument(UUID userId, byte[] content);
    String uploadPropertyDocument(UUID propertyId, byte[] content);
    String uploadPropertyImage(UUID propertyId, byte[] content);

    // Downloads
    byte[] downloadProfilePhoto(String key);
    byte[] downloadCreciDocument(String key);
    byte[] downloadPropertyDocument(String key);
    byte[] downloadPropertyImage(String key);

    // Deletes
    void deleteProfilePhoto(String key);
    void deleteCreciDocument(String key);
    void deletePropertyDocument(String key);
    void deletePropertyImage(String key);
}
