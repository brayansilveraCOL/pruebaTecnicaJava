package com.core.api.Entity;

import com.core.api.Enums.FundCategoryEnum;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.math.BigDecimal;


@DynamoDbBean
public class Fund {
    @Setter
    private String PK;
    @Setter
    private String SK;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private BigDecimal amountMin;
    @Getter
    @Setter
    private BigDecimal balance;
    @Getter
    @Setter
    private FundCategoryEnum category;

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
