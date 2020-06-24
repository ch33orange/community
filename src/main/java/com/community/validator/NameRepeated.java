package com.community.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameRepeatedValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameRepeated {

    String message() default "该用户名已被注册!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
