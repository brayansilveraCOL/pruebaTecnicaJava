package com.core.api.Service.interfaces;

import com.core.api.dto.ResponseDTO;
import com.core.api.dto.fund.FundDTO;
import com.core.api.dto.fund.SubscribeDTO;
import com.core.api.dto.fund.UnsubscribeDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

public interface FundService {
    ResponseDTO create(@Validated @RequestBody FundDTO fundDTO) throws Exception;
    ResponseDTO getAllFunds(String prefix) throws Exception;
    ResponseDTO subscribe(@Validated @RequestBody SubscribeDTO subscribeDTO) throws Exception;
    ResponseDTO unSubscribe(@Validated @RequestBody UnsubscribeDTO unsubscribeDTO) throws Exception;
}
