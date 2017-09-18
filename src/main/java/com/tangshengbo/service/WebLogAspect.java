package com.tangshengbo.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Created by Tang on 2017/6/26.
 */
@Aspect
@Component
public class WebLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    //两个..代表所有子目录，最后括号里的两个..代表所有参数
    @Pointcut("execution(public * com.tangshengbo.controller.*.*(..))")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("请求地址 : " + request.getRequestURL().toString());
        logger.info("HTTP METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        logger.info("参数 : " + Arrays.toString(joinPoint.getArgs()));
    }

    // returning的值和doAfterReturning的参数名一致
    @AfterReturning(returning = "ret", pointcut = "logPointCut()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("返回值 : " + ret);
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object ob = pjp.proceed();// ob 为方法的返回值
        logger.info("耗时 : " + (System.currentTimeMillis() - startTime) + "ms");
        return ob;
    }

    private String getUrl(HttpServletRequest request) {
        String url = request.getScheme() + "://"
                + request.getServerName() + ":"
                + request.getServerPort()
                + request.getContextPath()
                + request.getRequestURI();
        String url2 = request.getRequestURL().toString();
        return url;
    }
}
