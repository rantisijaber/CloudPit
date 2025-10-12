package com.jaberrantisi.backendlambda.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${BUCKET_NAME}")
    private String bucketName;



    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void saveToS3(byte[] file, String s3Key) throws IOException {

        PutObjectRequest putReq = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .contentType("text/csv")
                .build();
        s3Client.putObject(putReq, RequestBody.fromBytes(file));
    }
}
