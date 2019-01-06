package com.tangshengbo.service;

import com.tangshengbo.controller.BaseController;
import com.tangshengbo.core.JsonUtils;
import com.tangshengbo.model.HttpLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.AbstractErrors;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.text.Format;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by Tang on 2017/6/26.
 */
@Aspect
@Component
public class WebLogAspect extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

//    @DeclareParents(value = "com.tangshengbo.service.LogService+", defaultImpl = com.tangshengbo.service.impl.AccountServiceImpl.class )
//    private AccountService AccountService;

    private static final List<Class<?>> ignoreClasses = Arrays.asList(
            HttpSession.class, AbstractErrors.class, OncePerRequestFilter.class, ServletRequest.class,
            ServletResponse.class, Format.class, InputStream.class);


    @Autowired
    private LogService logService;

    //两个..代表所有子目录，最后括号里的两个..代表所有参数
    @Pointcut("execution(public * com.tangshengbo.controller.*.*(..)) && !bean(accountController) && !bean(myHandlerController)")
    public void logPointCut() {
    }
//
//    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
//    public void logPointCutWith() {
//    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestUrl = request.getRequestURL().toString();
        String httpMethod = request.getMethod();
        String clientIp = getClientIp(request);
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
        logger.info("参数 : " + toJsonString(joinPoint.getArgs()));

//        writeLog(requestUrl, httpMethod, clientIp, clientProxy);

    }

    private void writeLog(String requestUrl, String httpMethod, String clientIp, String clientProxy) {
        if (requestUrl.contains("/love/list")) {
            return;
        }
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
        if (Objects.isNull(ob)) {
            ob = pjp.proceed();// ob 为方法的返回值
        }
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

    private String toJsonString(Object[] objects) {
        String res = "";
        try {
            StringBuilder builder = new StringBuilder();
            for (Object object : objects) {
                if (isIgnoreClass(object)) {
                    res = object.getClass().getName();
                    continue;
                }
                builder.append(",");
                try {
                    res = JsonUtils.objectToJson(object);
                } catch (Exception e) {
                    res = object.getClass().getName();
                }
                builder.append(res);
            }
            res = StringUtils.hasText(builder.toString()) ? builder.toString().substring(1) : res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    private boolean isIgnoreClass(Object o) {
        if (null == o) {
            return false;
        }
        for (Class cls : ignoreClasses) {
            if (cls.isAssignableFrom(o.getClass())) {
                return true;
            }
        }
        return false;
    }
}
