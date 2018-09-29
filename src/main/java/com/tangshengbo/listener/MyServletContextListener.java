package com.tangshengbo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Enumeration;

/**
 * Created by Tangshengbo on 2018/9/29
 */
public class MyServletContextListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(MyServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        Enumeration<String> initParameterNames = servletContext.getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            String param = initParameterNames.nextElement();
            String value = servletContext.getInitParameter(param);
            logger.info("ServletContext-InitParameter: {}-{}", param, value);
        }
        logger.info("MyServletContextListener-contextInitialized:{}" , servletContextEvent.getServletContext().getContextPath());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("MyServletContextListener-contextDestroyed:{}" , servletContextEvent.getServletContext().getContextPath());
    }
}
