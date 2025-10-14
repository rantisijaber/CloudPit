package com.jaberrantisi.backendlambda.service;

import com.jaberrantisi.backendlambda.model.FileUpload;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Service
public class FileUploadService {

    private final DynamoDbEnhancedClient dynamoDbClient;

    public FileUploadService(DynamoDbEnhancedClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void saveToDynamo(String date, String fileId) {
        DynamoDbTable<FileUpload> userFileTable =
                dynamoDbClient.table("file-uploads", TableSchema.fromBean(FileUpload.class));

        FileUpload userFile = FileUpload.builder()
                .date(date)
                .fileId(fileId)
                .build();

        userFileTable.putItem(userFile);
    }

}
