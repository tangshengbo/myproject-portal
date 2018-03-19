package com.tangshengbo.service;

import com.tangshengbo.model.HttpLog;

import java.util.List;

/**
 * Created by Tangshengbo on 2018/3/5.
 */
public interface LogService {

    List<HttpLog> listHttpLog();

    void saveHttpLog(HttpLog httpLog);

    void complementIpAddress();
}
