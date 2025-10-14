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
public class FileUpload {
    private String date;
    private String fileId;

    @DynamoDbPartitionKey
    public String getDate() {
        return date;
    }

    @DynamoDbSortKey
    public String getFileId() {
        return fileId;
    }
}
