package com.community.validator;


import com.community.dao.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailRepeatedValidator implements ConstraintValidator<EmailRepeated,String> {

    @Autowired
    private UserExtMapper userExtMapper;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userExtMapper.selectByEmail(value)==null;
    }
}
