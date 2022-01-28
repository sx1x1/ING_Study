package com.sxr.study.springboot.validator;

import com.google.common.collect.Lists;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 手动校验参数工具类
 *
 * @author sxr
 * @date 2021/8/24 11:42 下午
 */
public class ValidateUtil {

    public static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    public static void validate(Object... params) {
        Assert.notEmpty(params, "校验参数不能为空");
        Set<ConstraintViolation<Object>> set = Arrays.stream(params)
                .map(VALIDATOR::validate)
                .filter(constraintSet -> !CollectionUtils.isEmpty(constraintSet))
                .reduce(Collections.emptySet(), (pre, next) -> Stream.concat(pre.stream(), next.stream()).collect(Collectors.toSet()));
        if (!CollectionUtils.isEmpty(set)) {
            // 抛出校验异常信息
            throw new ConstraintViolationException(set.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(",")), set);
        }
    }
}
