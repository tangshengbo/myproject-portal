package com.tangshengbo.service.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by TangShengBo on 2018/11/2
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(ApplicationContextHolder.class);

    private static ApplicationContext APPLICATIONCONTEXT;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("设置Bean开始.................Aware");
        ApplicationContextHolder.APPLICATIONCONTEXT = applicationContext;
    }

    public static ApplicationContext applicationContext() {
        return APPLICATIONCONTEXT;
    }

    public  ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
