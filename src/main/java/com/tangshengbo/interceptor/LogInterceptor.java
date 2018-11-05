package com.tangshengbo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Tangshengbo on 2018/11/5
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        String basePath = request.getContextPath();
        String realUri = requestUri.replace(basePath, "");
        logger.info("{}", handler);
        logger.info("requestUri:{},basePath:{},realUri:{}", requestUri, basePath, realUri);
        return true;
    }
}
