package com.tangshengbo.service.component;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

/**
 * Created by TangShengBo on 2018/11/13
 */
@Component
public class MyApplicationContextEvent extends ApplicationContextEvent {

    private String applicationName;

    private String eventName;

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public MyApplicationContextEvent(ApplicationContext source) {
        super(source);
        applicationName = source.getId();
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getEventName() {
        return eventName;
    }
}
