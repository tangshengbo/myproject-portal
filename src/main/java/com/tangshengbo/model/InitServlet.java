package com.tangshengbo.model;

import com.tangshengbo.service.component.ApplicationContextHolder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StopWatch;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Tangshengbo on 2018/11/5
 */
public class InitServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(InitServlet.class);

    @Override
    public void init() {
        logger.info("初始化............................");
        StopWatch watch = new StopWatch();
        watch.start();
        Map<String, Object> beans = new LinkedHashMap<>();
        ApplicationContext applicationContext = ApplicationContextHolder.applicationContext();
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            Object bean = applicationContext.getBean(name);

            beans.put(name, bean);
        }
        beans.forEach((name, bean) -> logger.info("{} --> {}", name, ReflectionToStringBuilder.toString(bean, ToStringStyle.MULTI_LINE_STYLE)));
        watch.stop();
        logger.info("初始化完成:{} s", watch.getTotalTimeSeconds());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");

        try (ServletOutputStream outputStream = resp.getOutputStream()){
            IOUtils.write("初始化完成",outputStream, "UTF-8");
            outputStream.flush();
        }
    }
}
