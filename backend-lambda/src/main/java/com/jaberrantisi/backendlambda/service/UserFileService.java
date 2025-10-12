package com.jaberrantisi.backendlambda.service;

import com.jaberrantisi.backendlambda.model.UserFile;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Service
public class UserFileService {

    private final DynamoDbEnhancedClient dynamoDbClient;

    public UserFileService(DynamoDbEnhancedClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void saveToDynamo(String userId, String fileId, String s3Key) {
        DynamoDbTable<UserFile> userFileTable =
                dynamoDbClient.table("userFiles", TableSchema.fromBean(UserFile.class));

        UserFile userFile = UserFile.builder()
                .userId(userId)
                .fileId(fileId)
                .s3Key(s3Key)
                .build();

        userFileTable.putItem(userFile);
    }

}
