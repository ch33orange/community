package com.community.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailCodeValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailCode {
    String message() default "验证码错误，邮箱验证失败!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
