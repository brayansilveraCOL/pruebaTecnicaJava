package com.core.api.dto.fund;

import com.core.api.Enums.FundCategoryEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FundDTO {
    private String name;
    private String code;
    private BigDecimal amountMin;
    private FundCategoryEnum category;
}
