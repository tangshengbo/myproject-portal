package com.tangshengbo.core;

import com.alibaba.fastjson.JSON;
import com.tangshengbo.enums.ResponseCode;

import java.io.Serializable;


/**
 * Created by tangshengbo on 2017/1/10.
 */
public class ResponseMessage<T> implements Serializable {

    private static final long serialVersionUID = 2478271177910606374L;
    private int code;
    private String message;
    private T data;

    public ResponseMessage setCode(ResponseCode responseCode) {
        this.code = responseCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ResponseMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseMessage setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
