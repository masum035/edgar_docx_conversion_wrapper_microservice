package com.wsd.edgardocswrapper.serviceImplementors;

import com.github.f4b6a3.ulid.UlidCreator;
import com.wsd.edgardocswrapper.services.IMinioService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements IMinioService {

    private final MinioClient minioClient;

//    @Value("${minio.bucket.name}")
//    private String bucketName;

    @Value("${minio.url}")
    private String minioUrl;


    @Override
    public Mono<Void> uploadFile(String bucketName, MultipartFile file) {
        return Mono.fromCallable(() -> {
            String fileName = generateFileName(file);
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(fileName) // Use the file name as object name
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
                System.out.println("File uploaded to MinIO successfully, url: " + minioUrl + "/" + bucketName + "/" + fileName);
            } catch (Exception ex) {
                throw new RuntimeException("Error occurred: " + ex.getMessage());
            }
            return null;
        }).then();
    }

    public String generateFileName(MultipartFile file) {
        return UlidCreator.getMonotonicUlid().toString() + "-" + Objects.requireNonNull(file.getOriginalFilename()).replace(" ", "_");
    }
}
