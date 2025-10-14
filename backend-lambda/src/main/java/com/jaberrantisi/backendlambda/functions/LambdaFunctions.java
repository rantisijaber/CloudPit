package com.jaberrantisi.backendlambda.functions;

import com.jaberrantisi.backendlambda.service.AwsCsvService;
import com.jaberrantisi.backendlambda.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;


@Configuration
public class LambdaFunctions {
    private final AwsCsvService csvService;
    private final FileUploadService fileUploadService;

    @Autowired
    public LambdaFunctions(AwsCsvService csvService, FileUploadService fileUploadService) {
        this.csvService = csvService;
        this.fileUploadService = fileUploadService;
    }

    @Bean
    public Supplier<Void> pingLambda() {
        return () -> null;
    }

    @Bean
    public Function<Map<String, Object>, Map<String, Map<String, Double>>> uploadCsv() {
        return event -> {
            try {
                Object bodyObj = event.get("body");
                if (bodyObj == null) {
                    throw new RuntimeException("No body found in request");
                }

                boolean isBase64 = Boolean.TRUE.equals(event.get("isBase64Encoded"));

                byte[] csvBytes;
                if (bodyObj instanceof String bodyStr) {
                    csvBytes = isBase64 ? Base64.getDecoder().decode(bodyStr)
                            : bodyStr.getBytes(StandardCharsets.UTF_8);
                } else if (bodyObj instanceof byte[]) {
                    csvBytes = (byte[]) bodyObj;
                } else {
                    throw new RuntimeException("Unsupported body type: " + bodyObj.getClass());
                }
                String fileId = UUID.randomUUID().toString();
                String date = LocalDate.now().toString();

                fileUploadService.saveToDynamo(date, fileId);
                return csvService.groupByDate(csvService.parseCsv(csvBytes));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }







}
