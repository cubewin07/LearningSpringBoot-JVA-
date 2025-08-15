package org.example.file_uploading_service.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class R2_service {
    private final S3Client s3;
    @Value("${cloudflare.r2.bucketName}")
    private String bucketName;

    @Value("${CLOUDINARY_URL}")
    private String cloudinaryUrl;

    Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);

    public void uploadFile(String key, byte[] data, String contentType){

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .build();
        s3.putObject(request, RequestBody.fromBytes(data));
    }

    public void uploadLargeFile(String key, Path path) throws IOException {


        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(Files.probeContentType(path))
                .build();
        s3.putObject(request, RequestBody.fromFile(path));
    }

    public void upLoadStream(String key, InputStream inputStream, Long contentSize, String contentType) throws IOException {

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .build();
        s3.putObject(request, RequestBody.fromInputStream(inputStream, contentSize));
    }
}
