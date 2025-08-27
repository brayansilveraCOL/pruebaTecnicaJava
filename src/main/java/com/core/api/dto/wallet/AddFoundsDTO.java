package com.core.api.dto.wallet;

import com.core.api.Validators.anotate.Cedula;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddFoundsDTO {
    @Cedula
    @NotEmpty
    private String identify;
    @Positive
    private BigDecimal amount;
}
