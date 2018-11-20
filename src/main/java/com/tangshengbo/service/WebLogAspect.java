package com.tangshengbo.service;

import com.tangshengbo.controller.BaseController;
import com.tangshengbo.model.HttpLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WebLogAspect extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @DeclareParents(value = "com.tangshengbo.service.impl.LogServiceImpl", defaultImpl = com.tangshengbo.service.impl.AccountServiceImpl.class )
    private AccountService AccountService;

    @Autowired
    private LogService logService;

    //两个..代表所有子目录，最后括号里的两个..代表所有参数
    @Pointcut("execution(public * com.tangshengbo.controller.*.*(..))")
    public void logPointCut() {
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void logPointCutWith() {
    }

    @Before("logPointCutWith()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestUrl = request.getRequestURL().toString();
        String httpMethod = request.getMethod();
        String clientIp = getIpAddr(request);
        String clientProxy = request.getHeader("User-Agent");

        MDC.put("clientIp", clientIp);
        MDC.put("clientProxy", clientProxy);

        // 记录下请求内容
        logger.info("请求地址 : {}", requestUrl);
        logger.info("HTTP METHOD : {}", httpMethod);
        logger.info("IP : {}", clientIp);
        logger.info("代理 : {}", clientProxy);
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        logger.info("参数 : " + Arrays.toString(joinPoint.getArgs()));

        writeLog(requestUrl, httpMethod, clientIp, clientProxy);

    }

    private void writeLog(String requestUrl, String httpMethod, String clientIp, String clientProxy) {
        if (!requestUrl.contains("/log/")) {
            HttpLog httpLog = new HttpLog();
            httpLog.setClientIp(clientIp);
            httpLog.setClientProxy(clientProxy);
            httpLog.setHttpMethod(httpMethod);
            httpLog.setRequestUrl(requestUrl);
            logService.saveHttpLog(httpLog);
        }
    }

    // returning的值和doAfterReturning的参数名一致
    @AfterReturning(returning = "ret", pointcut = "logPointCut()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("返回值 : " + ret);
        MDC.clear();
//        logger.info("{}", MDC.getCopyOfContextMap().size());
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object ob = pjp.proceed();// ob 为方法的返回值
        logger.info("耗时 : " + (System.currentTimeMillis() - startTime) + "ms");
        return ob;
    }

//    @After(value = "logPointCut()")
//    public void doAfter(JoinPoint joinPoint) {}

//    @AfterThrowing(value = "logPointCut()", throwing = "ex")
//    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
//        logger.error("{}", ExceptionUtils.getStackTrace(ex));
//    }

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
