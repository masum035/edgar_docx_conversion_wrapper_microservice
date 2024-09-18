package com.wsd.edgardocswrapper.serviceImplementors;

import com.github.f4b6a3.ulid.UlidCreator;
import com.wsd.edgardocswrapper.services.IFileStrorageService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MinioFileStorageSeviceImpl implements IFileStrorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Value("${minio.url}")
    private String minioUrl;
    
    public String uploadFile(MultipartFile file) {
        try {
            String fileName = generateFileName(file);
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(file.getInputStream(), file.getInputStream().available(), -1)
                    .contentType(file.getContentType())
                    .build());
            return minioUrl + "/" + bucketName + "/" + fileName;
        } catch (Exception ex) {
            throw new RuntimeException("Error occurred: " + ex.getMessage());
        }
    }
    
    public String generateFileName(MultipartFile file) {
        return UlidCreator.getMonotonicUlid().toString() + "-" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
    }
}
