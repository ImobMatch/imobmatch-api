package br.com.imobmatch.api.infra.s3.services;

public interface S3Service {
    String uploadFile(String path, byte[] content, String contentType);
    byte[] downloadFile(String key);
    void deleteFile(String key);
}
