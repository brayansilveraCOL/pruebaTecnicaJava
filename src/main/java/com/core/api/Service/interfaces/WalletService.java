package com.core.api.Service.interfaces;

import com.core.api.dto.wallet.AddFoundsDTO;
import com.core.api.dto.wallet.DiscountFundsDTO;

public interface WalletService {
    Boolean fundsWallet(AddFoundsDTO addFoundsDTO) throws Exception;
    Boolean discountFundsWallet(DiscountFundsDTO discountFoundsDTO) throws Exception;
}
