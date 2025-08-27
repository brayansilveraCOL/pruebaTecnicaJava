package com.core.api.Entity;

import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.math.BigDecimal;
import java.time.Instant;

@DynamoDbBean
public class UserFund {
    @Setter
    private String PK;
    @Setter
    private String SK;
    @Getter
    @Setter
    private Instant createdAt;
    @Getter
    @Setter
    private Instant updatedAt;
    @Getter
    @Setter
    private Boolean isActive;
    @Getter
    @Setter
    private BigDecimal amount;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("PK")
    public String getPK() {
        return PK;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("SK")
    public String getSK() {
        return SK;
    }
}
