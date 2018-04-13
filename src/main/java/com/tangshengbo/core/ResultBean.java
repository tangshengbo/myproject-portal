package com.tangshengbo.core;

import java.io.Serializable;

/**
 * Created by Tangshengbo on 2018/4/13.
 */
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = -2534184002974603944L;

    private String resultCode;

    private String resultMsg;

    private T data;

    public ResultBean(T data) {
        this.data = data;
    }

    public ResultBean(Throwable e) {
        this.resultMsg = e.getMessage();
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
