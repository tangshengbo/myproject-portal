package com.tangshengbo.model;

import com.alibaba.fastjson.JSON;

/**
 * Created by Tangshengbo on 2018/10/19
 */
public class User {

    private String userName;

    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
