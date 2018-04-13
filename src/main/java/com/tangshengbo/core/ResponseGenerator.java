package com.tangshengbo.core;


import com.tangshengbo.enums.ResponseCode;

/**
 * 响应结果生成工具
 */
@SuppressWarnings("unchecked")
public class ResponseGenerator {

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static ResponseMessage genSuccessResult() {
        return new ResponseMessage().setCode(ResponseCode.SUCCESS).setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> ResponseMessage<T> genSuccessResult(T data) {
        return genSuccessResult().setData(data);
    }

    public static <T> ResponseMessage<T> genFailResult(String message) {
        return genFailResult().setMessage(message);
    }

    private static ResponseMessage genFailResult() {
        return new ResponseMessage().setCode(ResponseCode.FAIL);
    }

    public static <T> ResponseMessage<T> genServerErrorResult(String message) {
        return genFailResult().setCode(ResponseCode.INTERNAL_SERVER_ERROR).setMessage(message);
    }
}
