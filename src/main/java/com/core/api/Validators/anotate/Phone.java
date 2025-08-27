package com.core.api.Validators.anotate;
import com.core.api.Validators.validator.MobileCOValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MobileCOValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT })
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {
    String message() default "{phone.mobile.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
