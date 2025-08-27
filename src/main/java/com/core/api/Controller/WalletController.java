package com.core.api.Controller;

import com.core.api.Service.interfaces.WalletService;
import com.core.api.dto.ResponseDTO;
import com.core.api.dto.wallet.AddFoundsDTO;
import com.core.api.dto.wallet.DiscountFundsDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@Validated
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping(value = "health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping(value = "addFunds")
    public ResponseEntity<ResponseDTO> addFunds(@Valid @RequestBody AddFoundsDTO funds) throws Exception {
        Boolean success = walletService.fundsWallet(funds);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(success);
        responseDTO.setMessage("Funds Wallet Created");
        responseDTO.setData(funds);
        return ResponseEntity.ok(responseDTO);
    }
    @PostMapping(value = "discountFunds")
    public ResponseEntity<ResponseDTO> discountFunds(@Valid @RequestBody DiscountFundsDTO funds) throws Exception {
        Boolean success = walletService.discountFundsWallet(funds);
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setSuccess(success);
        responseDTO.setMessage("Funds Wallet Discount Created");
        responseDTO.setData(funds);
        return ResponseEntity.ok(responseDTO);
    }
}
