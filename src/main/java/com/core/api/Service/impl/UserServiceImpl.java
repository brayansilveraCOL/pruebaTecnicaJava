package com.core.api.Service.impl;

import com.core.api.Constants.DefaultKeys;
import com.core.api.Entity.User;
import com.core.api.Entity.Wallet;
import com.core.api.Enums.TypeRecordsEnum;
import com.core.api.Repository.SingleRepository;
import com.core.api.Service.interfaces.UserService;
import com.core.api.dto.ResponseDTO;
import com.core.api.dto.user.ResponseUserPutDTO;
import com.core.api.dto.user.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final SingleRepository singleRepository;

    public UserServiceImpl(SingleRepository singleRepository) {
        this.singleRepository = singleRepository;
    }

    @Override
    public ResponseDTO create(UserDTO user) {
        User userEntity = new User();
        Wallet walletEntity = new Wallet();
        ResponseDTO  dto = new ResponseDTO();
        ResponseUserPutDTO responseUserPutDTO = new ResponseUserPutDTO();
        try {
            User userExist = singleRepository.getFirstByPkSkPrefix(DefaultKeys.PFX_USER + user.getIdentify(), DefaultKeys.PFX_USER);

            if (userExist != null) {
                singleRepository.upsertUser(userExist);
                dto.setMessage("User already exist");
                dto.setSuccess(Boolean.TRUE);
                responseUserPutDTO.setUserId(userExist.getPK());
                dto.setData(responseUserPutDTO);
                return dto;
            }

            userEntity.setSK(DefaultKeys.PFX_USER + user.getEmail());
            userEntity.setPK(DefaultKeys.PFX_USER + user.getIdentify());
            userEntity.setEmail(user.getEmail());
            userEntity.setName(user.getName());
            userEntity.setLastName(user.getLastName());
            userEntity.setPhone(user.getPhone());
            userEntity.setIdentify(user.getIdentify());
            userEntity.setCreatedAt(Instant.now());
            userEntity.setUpdatedAt(Instant.now());
            userEntity.setType_record(TypeRecordsEnum.USER);

            walletEntity.setPK(DefaultKeys.PFX_USER + user.getIdentify());
            walletEntity.setSK(DefaultKeys.PFX_WALLET + user.getIdentify());
            walletEntity.setUserId(user.getIdentify());
            walletEntity.setWalletId(DefaultKeys.PFX_WALLET + user.getIdentify());
            walletEntity.setBalance(new BigDecimal("500000"));
            walletEntity.setIsActive(true);
            walletEntity.setCurrency("COP");
            walletEntity.setCreatedAt(Instant.now());
            walletEntity.setUpdatedAt(Instant.now());
            walletEntity.setType_record(TypeRecordsEnum.WALLET);

            singleRepository.putUser(userEntity);
            singleRepository.putWallet(walletEntity);

            responseUserPutDTO.setUserId(userEntity.getPK());
            responseUserPutDTO.setWalletId(walletEntity.getWalletId());
            dto.setData(responseUserPutDTO);
            dto.setMessage("User created");
            dto.setSuccess(true);
            return dto;
        }catch (Exception e){
            dto.setMessage(Arrays.toString(e.getStackTrace()));
            dto.setSuccess(Boolean.FALSE);
            return dto;
        }
    }
}
