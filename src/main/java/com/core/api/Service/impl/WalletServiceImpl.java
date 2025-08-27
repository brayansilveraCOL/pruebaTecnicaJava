package com.core.api.Service.impl;

import com.core.api.Constants.DefaultKeys;
import com.core.api.Entity.Wallet;
import com.core.api.Repository.SingleRepository;
import com.core.api.Service.interfaces.WalletService;
import com.core.api.dto.wallet.AddFoundsDTO;
import com.core.api.dto.wallet.DiscountFundsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {
    private final SingleRepository singleRepository;

    public WalletServiceImpl(SingleRepository singleRepository) {
        this.singleRepository = singleRepository;
    }

    @Override
    public Boolean fundsWallet(AddFoundsDTO addFoundsDTO) throws Exception {
        Wallet wallet = singleRepository.getWallet(DefaultKeys.PFX_USER + addFoundsDTO.getIdentify(), DefaultKeys.PFX_WALLET + addFoundsDTO.getIdentify());
        if(wallet == null){
            throw new Exception("Wallet Not Found");
        }
        wallet.setBalance(wallet.getBalance().add(addFoundsDTO.getAmount()));
        singleRepository.upsertWallet(wallet);
        return Boolean.TRUE;
    }

    @Override
    public Boolean discountFundsWallet(DiscountFundsDTO discountFoundsDTO) throws Exception {
        Wallet wallet = singleRepository.getWallet(DefaultKeys.PFX_USER + discountFoundsDTO.getIdentify(), DefaultKeys.PFX_WALLET + discountFoundsDTO.getIdentify());
        if(wallet == null){
            throw new Exception("Wallet Not Found");
        }
        if(wallet.getBalance().equals(new BigDecimal("0"))){
            throw new Exception("Wallet Balance Not Enough");
        }
        if(wallet.getBalance().compareTo(discountFoundsDTO.getAmount()) < 0){
            throw new Exception("not enough balance");
        }
        wallet.setBalance(wallet.getBalance().subtract(discountFoundsDTO.getAmount()));
        singleRepository.upsertWallet(wallet);
        return Boolean.TRUE;
    }
}
