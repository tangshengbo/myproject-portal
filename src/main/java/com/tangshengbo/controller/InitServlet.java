package com.tangshengbo.controller;

import com.alibaba.fastjson.JSON;
import com.tangshengbo.core.ResponseGenerator;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Tangshengbo on 2018/11/5
 */
public class InitServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(InitServlet.class);

    @Override
    public void init() {
        logger.info("初始化............................");
//        StopWatch watch = new StopWatch();
//        watch.start();
//        Map<String, Object> beans = new LinkedHashMap<>();
//        ApplicationContext applicationContext = ApplicationContextHolder.applicationContext();
//        String[] names = applicationContext.getBeanDefinitionNames();
//        for (String name : names) {
//            Object bean = applicationContext.getBean(name);
//
//            beans.put(name, bean);
//        }
//        beans.forEach((name, bean) -> logger.info("{} --> {}", name, ReflectionToStringBuilder.toString(bean, ToStringStyle.MULTI_LINE_STYLE)));
//        watch.stop();
//        logger.info("初始化完成:{} s", watch.getTotalTimeSeconds());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        try (ServletOutputStream outputStream = resp.getOutputStream()) {
            IOUtils.write(JSON.toJSONString(ResponseGenerator.genSuccessResult("初始化完成")), outputStream, "UTF-8");
            outputStream.flush();
        }
    }
}
