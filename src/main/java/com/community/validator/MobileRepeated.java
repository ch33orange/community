package com.community.validator;

import com.community.validator.*;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MobileRepeatedValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MobileRepeated {

    String message() default "该手机号已被注册!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
