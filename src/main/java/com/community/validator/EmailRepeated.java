package com.community.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailRepeatedValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailRepeated {
    String message() default "该邮箱已被注册!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
