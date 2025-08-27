package com.core.api.Controller;

import com.core.api.Entity.Fund;
import com.core.api.Service.interfaces.FundService;
import com.core.api.dto.ResponseDTO;
import com.core.api.dto.fund.FundDTO;
import com.core.api.dto.fund.SubscribeDTO;
import com.core.api.dto.fund.UnsubscribeDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/fund")
@Validated
public class FundController {

    private final FundService fundService;

    public FundController(FundService fundService) {
        this.fundService = fundService;
    }

    @GetMapping(value = "health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "subscribe")
    public ResponseEntity<ResponseDTO> subscribe(@Validated @RequestBody SubscribeDTO subscribeDTO) throws Exception {
        ResponseDTO responseDTO = fundService.subscribe(subscribeDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping(value = "unsubscribe")
    public ResponseEntity<ResponseDTO> unsubscribe(@Validated @RequestBody UnsubscribeDTO unsubscribeDTO) throws Exception {
        ResponseDTO responseDTO = fundService.unSubscribe(unsubscribeDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping(value = "create")
    public ResponseEntity<ResponseDTO> create(@Validated @RequestBody FundDTO fundDTO) throws  Exception {
        ResponseDTO responseDTO = fundService.create(fundDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping(value = "getAll")
    public ResponseEntity<ResponseDTO> getAllFunds() throws Exception {
        ResponseDTO responseDTO = fundService.getAllFunds("FUND#");
        return ResponseEntity.ok(responseDTO);
    }

}
