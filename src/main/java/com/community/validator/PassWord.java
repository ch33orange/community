package com.community.validator;


import javax.validation.*;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassWord {
    String message() default "必须包含字母、数字、符号(@$-)中至少2种!(长度8~15位)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
