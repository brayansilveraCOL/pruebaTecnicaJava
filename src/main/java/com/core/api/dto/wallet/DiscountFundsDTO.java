package com.core.api.dto.wallet;

import com.core.api.Validators.anotate.Cedula;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DiscountFundsDTO {
    @Cedula
    @NotEmpty
    private String identify;
    private BigDecimal amount;
}
