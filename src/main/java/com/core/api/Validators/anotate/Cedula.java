package com.core.api.Validators.anotate;

import com.core.api.Validators.validator.DocumentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DocumentValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(RetentionPolicy.RUNTIME)
public @interface Cedula {
    String message() default "{cedula.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}