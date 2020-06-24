package com.community.validator;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailCodeValidator  implements ConstraintValidator<EmailCode,String> {

    @Autowired
    private HttpServletRequest request;


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        String emailCode = (String)request.getSession().getAttribute("emailCode");


        return StringUtils.equals(value,emailCode);
    }
}
