package com.sxr.study.springboot.validator;

import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author sxr
 * @date 2021/8/24 11:42 下午
 */
@Component
public class ValidateUtil {
    public static Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public static String validate(Object... args){
        return null;
    }
}
