package com.core.api.Validators.validator;

import com.core.api.Validators.anotate.Cedula;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocumentValidator implements ConstraintValidator<Cedula, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (value == null || value.isBlank()) return true;
        String digits = value.replaceAll("\\D", "");
        return digits.matches("\\d{6,10}");
    }
}