package com.tangshengbo.core;

import com.alibaba.fastjson.JSON;
import com.tangshengbo.enums.ResponseCode;


/**
 * Created by tangshengbo on 2017/1/10.
 */
public class ResponseMessage {

    private int code;
    private String message;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public ResponseMessage setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


}
