package com.sxr.study.springboot.validator.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author sxr
 * @date 2021/8/25 2:40 下午
 */
public class MyValidator implements ConstraintValidator<Custom, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // 自定义校验注解的核心校验逻辑
        return s == null || s.length() == 0;
    }

    @Override
    public void initialize(Custom constraintAnnotation) {
        // do nothing
    }
}
