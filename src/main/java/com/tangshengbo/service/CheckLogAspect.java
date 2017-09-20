package com.tangshengbo.service;

import com.tangshengbo.core.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Created by Tangshengbo on 2017/9/19.
 */
@Aspect
@Component
public class CheckLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(CheckLogAspect.class);

//    @Autowired
//    private CheckLogService checkLogService;

    @Pointcut("@annotation(com.tangshengbo.core.Log)")
    public void logPointCut() {

    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 连接点
     */
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //请求的IP
        String ip = request.getRemoteAddr();
        try {
            //控制台输出
            logger.info("=====前置通知开始=====");
            Object object = joinPoint.getTarget();
            logger.info("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            logger.info("请求IP:" + ip);
            //构造数据库日志对象

            //保存数据库

            logger.info("=====前置通知结束=====");
        } catch (Exception e) {
            //记录本地异常日志
            logger.error("==前置通知异常==");
            logger.error("异常信息:{}", e.getMessage());
        }
    }

    @AfterThrowing(pointcut = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        try {
            //控制台输出
            logger.info("=====异常通知开始=====");
            logger.info("异常代码:" + e.getClass().getName());
            logger.info("异常信息:" + e.getMessage());
            logger.info("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            logger.info("方法描述:" + getServiceMethodDescription(joinPoint));
            //构造数据库日志对象

            //保存数据库

            logger.info("=====异常通知结束=====");
        } catch (Exception ex) {
            //记录本地异常日志
            logger.error("==异常通知异常==");
            logger.error("异常信息:{}", ex);
        }
        //录本地异常日志
        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());


    }

    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    private static String[] getServiceMethodDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String businessNam = "";
        String type = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    businessNam = method.getAnnotation(Log.class).businessName();
                    type = method.getAnnotation(Log.class).type();
                    break;
                }
            }
        }
        return new String[]{type, businessNam};
    }

    /**
     * 管理员添加操作日志(后置通知)
     *
     * @param joinPoint
     * @param rtv
     * @throws Throwable
     */
    @AfterReturning(value = "logPointCut()", returning = "rtv")
    public void insertServiceCallCalls(JoinPoint joinPoint, Object rtv) throws Throwable {

        //获取方法名
        String methodName = joinPoint.getSignature().getName();
        String[] methodDescription = getServiceMethodDescription(joinPoint);
//        //创建日志对象
//        CheckLog checkLog = new CheckLog();
//        checkLog.setType(methodDescription[0]);
//        checkLog.setBusinessName(methodDescription[1]);
//        checkLog.setBeginDate(new Date());
//        checkLog.setCreateDate(new Date());
//        checkLog.setEndDate(new Date());
//        checkLog.setStatus("1");
//        checkLog.setRecordsCount(111);
//        checkLog.setDescription("23423423432");
//        checkLog.setCreateUser(Constants.DEFAULT_CREATE_USER);
//        checkLogService.insertLog(checkLog);
    }

//    /**
//     * 使用Java反射来获取被拦截方法(insert、update)的参数值，
//     * 将参数值拼接为操作内容
//     */
//    public String adminOptionContent(Object[] args, String mName) throws Exception {
//        if (args == null) {
//            return null;
//        }
//        StringBuffer rs = new StringBuffer();
//        rs.append(mName);
//        String className = null;
//        int index = 1;
//        // 遍历参数对象
//        for (Object info : args) {
//            //获取对象类型
//            className = info.getClass().getName();
//            className = className.substring(className.lastIndexOf(".") + 1);
//            rs.append("[参数" + index + "，类型：" + className + "，值：");
//            // 获取对象的所有方法
//            Method[] methods = info.getClass().getDeclaredMethods();
//            // 遍历方法，判断get方法
//            for (Method method : methods) {
//                String methodName = method.getName();
//                Object rsValue = null;
//                try {
//                    // 调用get方法，获取返回值
//                    rsValue = method.invoke(info);
//                    if (rsValue == null) {//没有返回值
//                        continue;
//                    }
//                } catch (Exception e) {
//                    continue;
//                }
//                //将值加入内容中
//                rs.append("(" + methodName + " : " + rsValue + ")");
//            }
//            rs.append("]");
//            index++;
//        }
//        return rs.toString();
//    }


}
