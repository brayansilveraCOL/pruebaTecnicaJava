package com.core.api.Entity;

import com.core.api.Enums.TypeRecordsEnum;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import java.math.BigDecimal;
import java.time.Instant;


@DynamoDbBean
public class Wallet {
    private String PK;
    private String SK;
    @Getter
    @Setter
    private String userId;
    @Setter
    @Getter
    private String walletId;
    @Setter
    @Getter
    private BigDecimal balance;
    @Getter
    @Setter
    private String currency;
    @Getter
    @Setter
    private Boolean isActive;
    @Getter
    @Setter
    private Instant createdAt;
    @Getter
    @Setter
    private Instant updatedAt;
    @Getter
    @Setter
    private Long version;
    @Setter
    @Getter
    private TypeRecordsEnum type_record;

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

    public void setPK(String PK) {
        this.PK = PK;
    }

    public void setSK(String SK) {
        this.SK = SK;
    }
}
