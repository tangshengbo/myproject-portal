package com.tangshengbo.service.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

/**
 * Created by TangShengBo on 2018/11/2
 */
@Component
public class MyBeanNameAware implements BeanNameAware {

    private static Logger logger = LoggerFactory.getLogger(MyBeanNameAware.class);

    @Override
    public void setBeanName(String name) {
        logger.info("{}", name);
    }
}
