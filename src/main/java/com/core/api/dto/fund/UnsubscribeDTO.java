package com.core.api.dto.fund;

import com.core.api.Validators.anotate.Cedula;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UnsubscribeDTO {
    @Cedula
    private String identify;
    private String codeEntity;
    private String nameEntity;
}
