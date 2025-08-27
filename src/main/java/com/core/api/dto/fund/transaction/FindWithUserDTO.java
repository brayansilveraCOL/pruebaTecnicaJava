package com.core.api.dto.fund.transaction;

import com.core.api.Validators.anotate.Cedula;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FindWithUserDTO {
    @NotBlank
    @NotNull
    @Cedula
    private String identify;
}
