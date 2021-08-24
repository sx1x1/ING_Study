package com.sxr.study.springboot.exception;

import lombok.Data;

/**
 * @author sxr
 * @date 2021/8/24 10:39 下午
 */
@Data
public class CommonResult<T> {
    private Boolean success;
    private Integer code;
    private String msg;
    private T data;

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setSuccess(Boolean.TRUE);
        result.setCode(200);
        result.setMsg(null);
        result.setData(data);
        return result;
    }

    public static CommonResult<String> fail(String msg){
        CommonResult<String> result = new CommonResult<>();
        result.setSuccess(Boolean.FALSE);
        result.setCode(400);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
}
