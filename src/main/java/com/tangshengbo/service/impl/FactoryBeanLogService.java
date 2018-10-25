package com.tangshengbo.service.impl;

import com.tangshengbo.model.HttpLog;
import com.tangshengbo.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Tangshengbo on 2018/10/25
 */
public class FactoryBeanLogService implements LogService {

    private static Logger logger = LoggerFactory.getLogger(FactoryBeanLogService.class);

    @Override
    public List<HttpLog> listHttpLog() {
        return null;
    }

    @Override
    public void saveHttpLog(HttpLog httpLog) {

    }

    @Override
    public void complementIpAddress() {
        logger.info("{}", "complementIpAddress");
    }
}
