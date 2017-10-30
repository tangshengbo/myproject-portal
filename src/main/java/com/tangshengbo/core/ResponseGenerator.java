package com.tangshengbo.core;


import com.tangshengbo.enums.ResponseCode;

/**
 * 响应结果生成工具
 */
public class ResponseGenerator {

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static ResponseMessage genSuccessResult() {
        return new ResponseMessage()
                .setCode(ResponseCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static ResponseMessage genSuccessResult(Object data) {
        return new ResponseMessage()
                .setCode(ResponseCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static ResponseMessage genFailResult(String message) {
        return new ResponseMessage()
                .setCode(ResponseCode.FAIL)
                .setMessage(message);
    }
}
