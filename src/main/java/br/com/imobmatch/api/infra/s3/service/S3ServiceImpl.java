package br.com.imobmatch.api.infra.s3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import br.com.imobmatch.api.infra.s3.repository.S3Repository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final S3Repository s3Repository;

    @Override
    public String uploadProfilePhoto(UUID userId, byte[] content) {
        return this.s3Repository.uploadProfilePhoto(userId, content);
    }


    @Override
    public String uploadPropertyImage(UUID propertyId, byte[] content) {
        return this.s3Repository.uploadPropertyImage(propertyId, content);
    }

    @Override
    public byte[] downloadProfilePhoto(String key) {
        return this.s3Repository.downloadProfilePhoto(key);
    }

    @Override
    public byte[] downloadPropertyImage(String key) {
        return this.s3Repository.downloadPropertyImage(key);
    }

    @Override
    public void deleteProfilePhoto(String key) {
        this.s3Repository.deleteProfilePhoto(key);
    }


    @Override
    public void deletePropertyImage(String key) {
        this.s3Repository.deletePropertyImage(key);
    }
}
