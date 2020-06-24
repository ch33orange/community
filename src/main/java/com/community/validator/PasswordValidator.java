package com.community.validator;


import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;

import javax.servlet.http.*;
import javax.validation.*;
import java.util.regex.*;

public class PasswordValidator implements ConstraintValidator<PassWord,String> {

    @Autowired
    private HttpServletRequest request;


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern Password_Pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)(?![-@$]+$)[-@$0-9A-Za-z]{8,16}$");
        Matcher matcher = Password_Pattern.matcher(value);
        return matcher.matches();
    }
}
