package com.tangshengbo.service.component;

import com.tangshengbo.service.LogService;
import com.tangshengbo.service.impl.FactoryBeanLogService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * Created by Tangshengbo on 2018/10/25
 */
@Component("userFactoryBean")
public class UserFactoryBean implements FactoryBean<LogService> {

    @Override
    public LogService getObject() throws Exception {
        return new FactoryBeanLogService();
    }
    @Override
    public Class<?> getObjectType() {
        return LogService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
