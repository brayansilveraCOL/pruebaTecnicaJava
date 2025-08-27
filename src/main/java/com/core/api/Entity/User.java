package com.core.api.Entity;

import com.core.api.Enums.TypeRecordsEnum;
import com.core.api.Validators.anotate.Cedula;
import com.core.api.Validators.anotate.Phone;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbVersionAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.Instant;

@DynamoDbBean
public class User {

    private String PK;
    private String SK;
    @Cedula
    @Setter
    @Getter
    private String identify;
    @Email
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String name;
    @Setter
    @Getter
    private String lastName;
    @Phone
    @Getter
    @Setter
    private String phone;
    @Getter
    @Setter
    private Instant createdAt;
    @Getter
    @Setter
    private Instant updatedAt;
    @Setter
    @Getter
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

    @DynamoDbVersionAttribute
    public Long getVersion() { return version; }

    public void setPK(String PK) {
        this.PK = PK;
    }

    public void setSK(String SK) {
        this.SK = SK;
    }
}
