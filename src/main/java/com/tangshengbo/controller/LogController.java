package com.tangshengbo.controller;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;
import com.google.gson.Gson;
import com.tangshengbo.core.ResponseGenerator;
import com.tangshengbo.core.ResponseMessage;
import com.tangshengbo.model.CanvasImage;
import com.tangshengbo.model.HttpLog;
import com.tangshengbo.model.LoveImage;
import com.tangshengbo.model.MyInject;
import com.tangshengbo.service.AccountService;
import com.tangshengbo.service.HttpLogService;
import com.tangshengbo.service.LogService;
import com.tangshengbo.service.component.ApplicationContextHolder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by Tangshengbo on 2018/3/5.
 */
@RestController
@RequestMapping("/log")
public class LogController {

    private static Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogService logService;

    @Autowired
    private HttpLogService dao;

    @MyInject
    private List<AccountService> accountServiceList;

    @MyInject
    private CommonSelfIdGenerator commonSelfIdGenerator;

    @MyInject
    private LoveImage loveImage;

    @Value("#{loveImage.imgUrl}")
    private String value;

    @Autowired
    private Gson gson;

    @MyInject
    private ApplicationContextHolder applicationContextHolder;

    // 本方法将处理 /courses/view?courseId=123 形式的URL
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage getHttpLogs() {
        return ResponseGenerator.genSuccessResult(logService.listHttpLog());
    }

    @RequestMapping(value = "/code", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage genVerificationCode() {
        return ResponseGenerator.genSuccessResult((int) (Math.random() * 900000) + 100000);
    }

    @RequestMapping(value = "/id", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage genId() {
        return ResponseGenerator.genSuccessResult(commonSelfIdGenerator.generateId().longValue());
    }

    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public ResponseMessage addHttpLog(@RequestBody HttpLog log) {
        logger.info("addHttpLog:{}", log);
        dao.saveUser(log);
        return ResponseGenerator.genSuccessResult(log);
    }

    @RequestMapping(value = "/find", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage selectHttpLogs(String clientIp) {
        logger.info("selectHttpLogs:{}", clientIp);
        return ResponseGenerator.genSuccessResult(dao.findHttpLogs(clientIp));
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ResponseMessage updateHttpLog(@RequestBody HttpLog log) {
        logger.info("updateHttpLog:{}", log);
        dao.updateHttpLog(log);
        return ResponseGenerator.genSuccessResult(log);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public ResponseMessage delete(int id) {
        logger.info("updateHttpLog:{}", log);
        dao.removeById(id);
        return ResponseGenerator.genSuccessResult(id);
    }

    @GetMapping("/love")
    public ResponseMessage getLoveImage() {
        loveImage.setCanvasImage(CanvasImage.canvasImages().get(0));
        loveImage.setImgUrl(value);
        loveImage.setId(commonSelfIdGenerator.generateId().intValue());
        return ResponseGenerator.genSuccessResult(loveImage);
    }

    @PostMapping("/name")
    public ResponseMessage getSpringContextName(String className) {
        ApplicationContext applicationContext = applicationContextHolder.getApplicationContext();
        try {
            Class<?> aClass = ClassUtils.forName(className, this.getClass().getClassLoader());
            String[] names = applicationContext.getBeanNamesForType(aClass, true, false);
            return ResponseGenerator.genSuccessResult(JSON.toJSONString(names));
        } catch (ClassNotFoundException e) {
            return ResponseGenerator.genFailResult(ExceptionUtils.getRootCauseMessage(e));
        }
    }

    @PostMapping("/value")
    public ResponseMessage getSpringContextValue(String className) {
        ApplicationContext applicationContext = applicationContextHolder.getApplicationContext();
        try {
            Class<?> aClass = ClassUtils.forName(className, this.getClass().getClassLoader());
            Map<String, ?> beansOfType = applicationContext.getBeansOfType(aClass, true, true);
            return ResponseGenerator.genSuccessResult(JSON.toJSONString(beansOfType));
        } catch (ClassNotFoundException e) {
            return ResponseGenerator.genFailResult(ExceptionUtils.getRootCauseMessage(e));
        }
    }

    @PostMapping("/bean")
    public ResponseMessage getSpringContextBean(String className) {
        ApplicationContext applicationContext = applicationContextHolder.getApplicationContext();
        Class<?> aClass = null;
        boolean isClass = true;
        try {
            aClass = ClassUtils.forName(className, this.getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            logger.info("{}", ExceptionUtils.getRootCauseMessage(e));
            isClass = false;
        }
        if (isClass) {
            return ResponseGenerator.genSuccessResult(ToStringBuilder.reflectionToString(applicationContext.getBean(aClass, true)));
        }
        return ResponseGenerator.genSuccessResult(ToStringBuilder.reflectionToString(applicationContext.getBean(className)));
    }
}
