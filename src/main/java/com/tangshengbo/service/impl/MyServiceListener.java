package com.tangshengbo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Created by TangShengBo on 2018/5/14.
 */
@Service
@DependsOn("gridFsTemplate")
public class MyServiceListener implements ApplicationListener<ContextStartedEvent> {

    private static Logger logger = LoggerFactory.getLogger(MyServiceListener.class);

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Override
    public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
        Environment environment = contextStartedEvent.getApplicationContext().getEnvironment();
        logger.info("MyServiceListener:{}", Arrays.toString(environment.getDefaultProfiles()));
    }

    @PostConstruct
    public void init() {
        logger.info("PostConstruct.............");
        gridFsTemplate.store(this.getClass().getResourceAsStream("/logback.xml"), "logback.xml");
    }
}
