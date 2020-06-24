package com.community.validator;


import com.community.dao.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileRepeatedValidator implements ConstraintValidator<MobileRepeated,String> {

    @Autowired
    private UserExtMapper userExtMapper;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //判断是否重复注册
        return userExtMapper.selectByMobile(value)==null;
    }
}
