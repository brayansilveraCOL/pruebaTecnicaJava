package com.core.api.Repository;

import com.core.api.Constants.DefaultKeys;
import com.core.api.Entity.*;
import com.core.api.Enums.TypeRecordsEnum;
import com.core.api.Enums.TypeTransactionsEnum;
import com.core.api.dto.fund.SubscribeDTO;
import com.core.api.dto.fund.UnsubscribeDTO;
import com.core.api.dto.user.ResponseUserPutDTO;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static com.core.api.Enums.TypeRecordsEnum.USER;
import static com.core.api.Enums.TypeRecordsEnum.WALLET;

@Repository
public class SingleRepository {
    private final DynamoDbTable<User> userTbl;
    private final DynamoDbTable<Wallet> walletTbl;
    private final DynamoDbTable<Fund> fundTbl;
    private final DynamoDbTable<UserFund> userFundTbl;
    private final DynamoDbTable<Transaction> transactionFundTbl;

    public SingleRepository(DynamoDbTable<User> userTbl, DynamoDbTable<Wallet> walletTbl, DynamoDbTable<Fund> fundTbl, DynamoDbTable<UserFund> userFundTbl, DynamoDbTable<Transaction> transactionFundTbl) {
        this.userTbl = userTbl;
        this.walletTbl = walletTbl;
        this.fundTbl = fundTbl;
        this.userFundTbl = userFundTbl;
        this.transactionFundTbl = transactionFundTbl;
    }

    public void putUser(User u) {
        userTbl.putItem(u);
    }

    public User getUser(String pk, String sk) {
        var key = Key.builder().partitionValue(pk).sortValue(sk).build();
        return userTbl.getItem(key);
    }

    public void upsertUser(User u) {
        u.setPhone(u.getPhone());
        u.setName(u.getName());
        u.setLastName(u.getLastName());
        u.setUpdatedAt(Instant.now());
        userTbl.putItem(u);
    }

    public void putWallet(Wallet w) {
        walletTbl.putItem(w);
    }

    public void upsertWallet(Wallet w) {
        w.setUpdatedAt(Instant.now());
        walletTbl.putItem(w);
    }

    public Wallet getWallet(String pk, String sk) {
        var key = Key.builder().partitionValue(pk).sortValue(sk).build();
        return walletTbl.getItem(key);
    }

    public User getFirstByPkSkPrefix(String pk, String prefix) {
        var req = QueryEnhancedRequest.builder()
                .queryConditional(
                        QueryConditional.sortBeginsWith(
                                Key.builder().partitionValue(pk).sortValue(prefix).build()
                        )
                )
                .limit(1)
                .build();

        var it = userTbl.query(req).items().iterator();
        return it.hasNext() ? it.next() : null;
    }

    public Fund getFund(String pk, String sk) {
        var key = Key.builder().partitionValue(pk).sortValue(sk).build();
        return fundTbl.getItem(key);
    }

    public void putFund(Fund f) {
        fundTbl.putItem(f);
    }

    public List<Fund> scanAllFundsByPrefix(String prefix) {
        var filter = Expression.builder()
                .expression("begins_with(#sk, :p)")
                .putExpressionName("#sk", "SK")
                .putExpressionValue(":p", AttributeValue.builder().s(prefix).build())
                .build();

        var req = ScanEnhancedRequest.builder()
                .filterExpression(filter)
                .build();

        var out = new ArrayList<Fund>();
        fundTbl.scan(req).items().forEach(out::add);
        return out;
    }

    public void putUserFund(UserFund userFund) {
        userFundTbl.putItem(userFund);
    }

    public void putTransaction(Transaction transaction) {
        transactionFundTbl.putItem(transaction);
    }

    public Optional<UserFund> findActiveUserFund(String identify, String fundCode) {
        String pk = DefaultKeys.PFX_USER + identify;
        String sk = DefaultKeys.PFX_FUND + fundCode;

        var key = Key.builder().partitionValue(pk).sortValue(sk).build();
        UserFund uf = userFundTbl.getItem(r -> r.key(key));

        return Optional.ofNullable(uf).filter(UserFund::getIsActive);
    }

    public Map subscribe(SubscribeDTO subscribeDTO) throws Exception {
        Map<String, Object> response = new HashMap<>();
        Wallet wallet  = getWallet(DefaultKeys.PFX_USER + subscribeDTO.getIdentify(), DefaultKeys.PFX_WALLET + subscribeDTO.getIdentify());
        if(wallet == null) {
            throw new Exception("Wallet not found");
        }
        if(wallet.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Wallet balance is negative or zero");
        }
        if(wallet.getBalance().compareTo(subscribeDTO.getAmount()) <= 0) {
            throw new Exception("Wallet balance is negative peer this transaction");
        }
        Fund fund = getFund(DefaultKeys.PFX_FUND + subscribeDTO.getNameEntity(), DefaultKeys.PFX_FUND + subscribeDTO.getCodeEntity());
        if(fund == null) {
            throw new Exception("Fund not found");
        }
        if (subscribeDTO.getAmount().compareTo(fund.getAmountMin()) < 0) {
            throw  new Exception("Amount must be greater than or equal to Fund amount min: " + fund.getAmountMin());
        }
        try {
            // Validate if User has not subscribed Fund
            Optional<UserFund> userFundExist = findActiveUserFund(subscribeDTO.getIdentify(), subscribeDTO.getCodeEntity());
            if(userFundExist.isPresent()) {
                throw new Exception("User Present Into Fund already exists: Fund: " + subscribeDTO.getNameEntity());
            }

            // Update My Balance Wallet
            wallet.setBalance(wallet.getBalance().subtract(subscribeDTO.getAmount()));
            wallet.setUpdatedAt(Instant.now());
            upsertWallet(wallet);

            // Update Balance Fund
            fund.setBalance(fund.getBalance().add(subscribeDTO.getAmount()));
            putFund(fund);

            // Create UserFund
            UserFund userFund = new UserFund();
            userFund.setPK(DefaultKeys.PFX_USER + subscribeDTO.getIdentify());
            userFund.setSK(DefaultKeys.PFX_FUND + subscribeDTO.getCodeEntity());
            userFund.setIsActive(Boolean.TRUE);
            userFund.setUpdatedAt(Instant.now());
            userFund.setCreatedAt(Instant.now());
            userFund.setAmount(subscribeDTO.getAmount());
            putUserFund(userFund);

            // Create transaction
            Transaction transaction = new Transaction();
            transaction.setPK(DefaultKeys.PFX_TX + DefaultKeys.PFX_USER + subscribeDTO.getIdentify() + subscribeDTO.getCodeEntity() + "_" + TypeTransactionsEnum.SUBSCRIBE);
            transaction.setSK(DefaultKeys.PFX_TX + DefaultKeys.PFX_WALLET + subscribeDTO.getIdentify() + subscribeDTO.getCodeEntity() + "_" + TypeTransactionsEnum.SUBSCRIBE);
            transaction.setCodeFunds(DefaultKeys.PFX_FUND + subscribeDTO.getCodeEntity());
            transaction.setAmount(subscribeDTO.getAmount());
            transaction.setUnique_code(UUID.randomUUID());
            putTransaction(transaction);

            response.put("wallet", wallet);
            response.put("userSubscribe", userFund);
            response.put("transaction", transaction);
            return response;
        }
        catch (Exception e) {
            // Reverse Transactions
            throw e;
        }
    }

    public Map unSubscribe(UnsubscribeDTO unsubscribeDTO) throws Exception {
        Map<String, Object> response = new HashMap<>();

        try {
            // Validate if User has not subscribed Fund
            Optional<UserFund> userFundExist = findActiveUserFund(unsubscribeDTO.getIdentify(), unsubscribeDTO.getCodeEntity());
            if(userFundExist.isEmpty()) {
                throw new Exception("User not stay Into Fund: " + unsubscribeDTO.getNameEntity());
            }

            // Restored Money to Wallet
            Wallet wallet  = getWallet(DefaultKeys.PFX_USER + unsubscribeDTO.getIdentify(), DefaultKeys.PFX_WALLET + unsubscribeDTO.getIdentify());
            wallet.setBalance(wallet.getBalance().add(userFundExist.get().getAmount()));
            wallet.setUpdatedAt(Instant.now());
            upsertWallet(wallet);

            // Update Balance Funds
            Fund fund = getFund(DefaultKeys.PFX_FUND + unsubscribeDTO.getNameEntity(), DefaultKeys.PFX_FUND + unsubscribeDTO.getCodeEntity());
            if(fund == null) {
                throw new Exception("Fund not found");
            }
            fund.setBalance(fund.getBalance().subtract(userFundExist.get().getAmount()));
            fundTbl.updateItem(fund);

            // Create transaction
            Transaction transaction = new Transaction();
            transaction.setPK(DefaultKeys.PFX_TX + DefaultKeys.PFX_USER + unsubscribeDTO.getIdentify() + unsubscribeDTO.getCodeEntity() + "_" + TypeTransactionsEnum.UNSUBSCRIBE);
            transaction.setSK(DefaultKeys.PFX_TX + DefaultKeys.PFX_WALLET + unsubscribeDTO.getIdentify() + unsubscribeDTO.getCodeEntity() + "_" + TypeTransactionsEnum.UNSUBSCRIBE);
            transaction.setCodeFunds(DefaultKeys.PFX_FUND + unsubscribeDTO.getCodeEntity());
            transaction.setAmount(BigDecimal.ZERO);
            transaction.setUnique_code(UUID.randomUUID());
            putTransaction(transaction);

            // Inactivate Relationship With Fund
            userFundExist.get().setIsActive(Boolean.FALSE);
            userFundExist.get().setUpdatedAt(Instant.now());
            userFundExist.get().setAmount(new BigDecimal(0));
            userFundTbl.updateItem(userFundExist.get());


            response.put("wallet", wallet);
            response.put("userSubscribe", userFundExist);
            response.put("transaction", transaction);
            return response;
        }catch (Exception e) {
            throw e;
        }

    }
}
