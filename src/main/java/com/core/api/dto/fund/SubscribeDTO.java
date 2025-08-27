package com.core.api.dto.fund;

import com.core.api.Validators.anotate.Cedula;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubscribeDTO {
    @Cedula
    String identify;
    BigDecimal amount;
    String codeEntity;
    String nameEntity;
}
