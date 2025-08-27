package com.core.api.Service.impl;

import com.core.api.Constants.DefaultKeys;
import com.core.api.Entity.Fund;
import com.core.api.Repository.SingleRepository;
import com.core.api.Service.interfaces.FundService;
import com.core.api.dto.ResponseDTO;
import com.core.api.dto.fund.FundDTO;
import com.core.api.dto.fund.SubscribeDTO;
import com.core.api.dto.fund.UnsubscribeDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service
public class FundServiceImpl implements FundService {
    private final SingleRepository singleRepository;


    public FundServiceImpl(SingleRepository singleRepository) {
        this.singleRepository = singleRepository;
    }

    @Override
    public ResponseDTO create(FundDTO fundDTO) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();

        Fund fund = singleRepository.getFund(DefaultKeys.PFX_FUND + fundDTO.getCode(), DefaultKeys.PFX_FUND + fundDTO.getName());
        if(fund != null) {
            fund.setName(fundDTO.getName());
            fund.setAmountMin(fundDTO.getAmountMin());
            fund.setCategory(fundDTO.getCategory());
            singleRepository.putFund(fund);
            responseDTO.setSuccess(true);
            responseDTO.setMessage("Fund Updated");
            responseDTO.setData(fund);
            return responseDTO;
        }
        try {
            fund = new Fund();
            fund.setPK(DefaultKeys.PFX_FUND + fundDTO.getName());
            fund.setSK(DefaultKeys.PFX_FUND + fundDTO.getCode());
            fund.setName(fundDTO.getName());
            fund.setAmountMin(fundDTO.getAmountMin());
            fund.setBalance(BigDecimal.ZERO);
            fund.setCategory(fundDTO.getCategory());
            singleRepository.putFund(fund);
            responseDTO.setMessage("Success");
            responseDTO.setData(fund);
            responseDTO.setSuccess(Boolean.TRUE);
            return responseDTO;
        }catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setMessage(e.getStackTrace().toString());
            return responseDTO;
        }
    }

    @Override
    public ResponseDTO getAllFunds(String prefix) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Fund> funds = singleRepository.scanAllFundsByPrefix(prefix);
            responseDTO.setSuccess(true);
            responseDTO.setData(funds);
            responseDTO.setMessage("Success");
            return responseDTO;
        }catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setMessage(e.getStackTrace().toString());
            return responseDTO;
        }
    }

    @Override
    public ResponseDTO subscribe(SubscribeDTO subscribeDTO) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Map userFund = singleRepository.subscribe(subscribeDTO);
            responseDTO.setSuccess(true);
            responseDTO.setData(userFund);
            responseDTO.setMessage("Success");
            return responseDTO;
        }catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setMessage(e.getMessage());
            return responseDTO;
        }
    }

    @Override
    public ResponseDTO unSubscribe(UnsubscribeDTO unsubscribeDTO) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Map userFund = singleRepository.unSubscribe(unsubscribeDTO);
            responseDTO.setSuccess(true);
            responseDTO.setData(userFund);
            responseDTO.setMessage("Success");
            return responseDTO;
        }catch (Exception e) {
            responseDTO.setSuccess(false);
            responseDTO.setMessage(e.getMessage());
            return responseDTO;
        }
    }
}
