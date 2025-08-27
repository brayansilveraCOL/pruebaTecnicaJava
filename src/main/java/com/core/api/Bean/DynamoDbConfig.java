package com.core.api.Bean;
import com.core.api.Entity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDbConfig {

    @Value("${aws.region}")
    private String region;

    @Value("${aws.dynamodb.table-name}")
    private String tableName;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
    @Bean
    public DynamoDbEnhancedClient enhancedClient(DynamoDbClient client) {
        return DynamoDbEnhancedClient.builder().dynamoDbClient(client).build();
    }
    @Bean
    public DynamoDbTable<User> userTable(DynamoDbEnhancedClient enhanced) {
        return enhanced.table(tableName, TableSchema.fromBean(User.class));
    }
    @Bean
    public DynamoDbTable<Wallet> walletTable(DynamoDbEnhancedClient enhanced) {
        return enhanced.table(tableName, TableSchema.fromBean(Wallet.class));
    }
    @Bean
    public DynamoDbTable<Fund> FundTable(DynamoDbEnhancedClient enhanced) {
        return enhanced.table(tableName, TableSchema.fromBean(Fund.class));
    }
    @Bean
    public DynamoDbTable<UserFund> UserFundTable(DynamoDbEnhancedClient enhanced) {
        return enhanced.table(tableName, TableSchema.fromBean(UserFund.class));
    }

    @Bean
    public DynamoDbTable<Transaction> TransactionTable(DynamoDbEnhancedClient enhanced) {
        return enhanced.table(tableName, TableSchema.fromBean(Transaction.class));
    }
}