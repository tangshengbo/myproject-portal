package com.tangshengbo.service.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by TangShengBo on 2018/5/14.
 */
@Component
public class MyApplicationListener implements ApplicationListener<MyApplicationContextEvent> {

    private static Logger logger = LoggerFactory.getLogger(MyApplicationListener.class);

    @Override
    public void onApplicationEvent(MyApplicationContextEvent event) {
        logger.info("{}:{}", event.getEventName(), event.getApplicationName());
    }
}
