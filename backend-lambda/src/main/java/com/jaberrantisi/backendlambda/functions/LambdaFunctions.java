package com.jaberrantisi.backendlambda.functions;

import com.auth0.jwt.JWT;
import com.jaberrantisi.backendlambda.service.AwsCsvService;
import com.jaberrantisi.backendlambda.service.S3Service;
import com.jaberrantisi.backendlambda.service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.messaging.Message;


@Configuration
public class LambdaFunctions {
    private final AwsCsvService csvService;
    private final UserFileService userFileService;
    private final S3Service s3Service;

    @Autowired
    public LambdaFunctions(AwsCsvService csvService, UserFileService userFileService, S3Service s3Service) {
        this.csvService = csvService;
        this.userFileService = userFileService;
        this.s3Service = s3Service;
    }

    @Bean
    public Supplier<Void> pingLambda() {
        return () -> null;
    }
    @Bean
    public Function<String, String> hello() {
        return test -> "hello world";
    }

    @Bean
    public Function<String, String> test() {
        return test -> "Test";
    }

    @Bean
    public Function<Message<InputStream>, Map<String, Map<String, Double>>> analyzeData() {
        return message -> {
            InputStream awsCsvFile = message.getPayload();
            String authHeader = message.getHeaders().get("Authorization", String.class);

            if (authHeader == null) throw new RuntimeException("Missing authorization header");

            String token = authHeader.substring("Bearer".length()).trim();
            String userId = JWT.decode(token).getSubject();
            String fileId = UUID.randomUUID().toString();
            String s3Key = "users/" + userId + "/files/" + fileId + ".csv";

            try {
                byte[] fileBytes = awsCsvFile.readAllBytes();

                s3Service.saveToS3(fileBytes, s3Key);

                userFileService.saveToDynamo(userId, fileId, s3Key);

                return csvService.groupByDate(csvService.parseCsv(fileBytes));

            } catch (IOException e) {
                throw new RuntimeException("Failed to upload or parse CSV", e);
            }
        };
    }


    @Bean
    public Function<Map<String, Object>, Map<String, Map<String, Double>>> testUpload() {
        return event -> {
            try {
                String body = (String) event.get("body");
                boolean isBase64 = Boolean.TRUE.equals(event.get("isBase64Encoded"));

                byte[] csvBytes;
                if (isBase64) {
                    csvBytes = Base64.getDecoder().decode(body);
                } else {
                    csvBytes = body.getBytes(StandardCharsets.UTF_8);
                }

                return csvService.groupByDate(csvService.parseCsv(csvBytes));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }






}
