package com.jaberrantisi.backendlambda.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;


@Builder @Setter
@NoArgsConstructor @AllArgsConstructor
@DynamoDbBean
public class UserFile {
    private String userId;
    private String fileId;
    private String s3Key;

    @DynamoDbPartitionKey
    public String getUserId() {
        return userId;
    }

    @DynamoDbSortKey
    public String getFileId() {
        return fileId;
    }
}
