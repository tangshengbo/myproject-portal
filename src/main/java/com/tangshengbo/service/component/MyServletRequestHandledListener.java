package com.tangshengbo.service.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * Created by TangShengBo on 2018/12/19
 */
@Component
public class MyServletRequestHandledListener implements ApplicationListener<ServletRequestHandledEvent> {

    private static Logger logger = LoggerFactory.getLogger(MyServletRequestHandledListener.class);

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
//        logger.info("请求完成:{}", event);
    }
}
