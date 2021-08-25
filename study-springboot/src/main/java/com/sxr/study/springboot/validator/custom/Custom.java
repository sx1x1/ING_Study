package com.sxr.study.springboot.validator.custom;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sxr
 * @date 2021/8/25 2:42 下午
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyValidator.class)
public @interface Custom {

    String message() default "字符串不能为空";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
