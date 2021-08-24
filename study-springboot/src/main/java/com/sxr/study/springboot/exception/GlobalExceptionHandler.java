package com.sxr.study.springboot.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

/**
 * @author sxr
 * @date 2021/8/24 10:36 下午
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResult handle(Exception e) {
        if (e instanceof ConstraintViolationException) {
            return CommonResult.fail(e.getMessage());
        }
        return CommonResult.fail(e.toString());
    }
}
