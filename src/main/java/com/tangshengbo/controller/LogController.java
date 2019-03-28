package com.tangshengbo.controller;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;
import com.google.gson.Gson;
import com.tangshengbo.cache.JedisClientPool;
import com.tangshengbo.core.ResponseGenerator;
import com.tangshengbo.core.ResponseMessage;
import com.tangshengbo.core.extension.MyInject;
import com.tangshengbo.core.extension.RequestParamDecode;
import com.tangshengbo.core.extension.ResponseBodyEncode;
import com.tangshengbo.model.CanvasImage;
import com.tangshengbo.model.HttpLog;
import com.tangshengbo.model.LoveImage;
import com.tangshengbo.service.AccountService;
import com.tangshengbo.service.HttpLogService;
import com.tangshengbo.service.LogService;
import com.tangshengbo.service.component.ApplicationContextHolder;
import com.tangshengbo.service.component.MyApplicationContextEvent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySources;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.*;

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

    @Value("#{dataSource.jdbcUrl}")
    private String imgUrl;

    @Autowired
    private Gson gson;

    @MyInject
    private ApplicationContextHolder applicationContextHolder;

    @Autowired
    private MyApplicationContextEvent contextEvent;

    @Qualifier("conversionServiceFactoryBean")
    @Autowired
    private ConversionService conversionService;

    @Value("#{propertyConfigurer.getAppliedPropertySources()}")
    private PropertySources propertySources;

    @Value("#{environment.getProperty('spring.profiles.default')}")
    private String profile;

    @Autowired
    private Environment environment;

    @Value("https://www.baidu.com/")
    private Resource url;

    @Autowired
    private JedisClientPool jedisClient;

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
        loveImage.setImgUrl(imgUrl);
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
        String beanInfo = ToStringBuilder.reflectionToString(applicationContext.getBean(className));
        String beanName = beanInfo.substring(0, beanInfo.indexOf("["));
        int indexOf = beanInfo.indexOf("[");
        int lastIndexOf = beanInfo.lastIndexOf("]");
        String beanPropertyStr = beanInfo.substring(indexOf + 1, lastIndexOf);
        String[] beanPropertyValues = beanPropertyStr.split(",");
        JSONArray jsonArray = new JSONArray(Arrays.asList(beanPropertyValues));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bean", beanName);
        jsonObject.put("property", jsonArray);
        return ResponseGenerator.genSuccessResult(jsonObject.toJSONString());
    }

    @GetMapping("/public-event")
    public ResponseMessage publicEvent(String eventName) {
        contextEvent.setEventName(eventName);
        applicationContextHolder.getApplicationContext().publishEvent(contextEvent);
        return ResponseGenerator.genSuccessResult(eventName);
    }

    @GetMapping("/convert")
    public ResponseMessage stringToDate(String date, HttpServletRequest request) {
        //暴露代理接口
        LogController proxy = (LogController) AopContext.currentProxy();
        proxy.exposeProxy(new Date(), "exposeProxy");
        conversionService = (ConversionService) request.getAttribute("org.springframework.core.convert.ConversionService");
        return ResponseGenerator.genSuccessResult(conversionService.convert(date, Date.class));
    }

    @PostMapping("/exposeProxy")
    public ResponseMessage exposeProxy(@RequestParam("str") Date str, @RequestParamDecode("content") String content) {
        logger.info("exposeProxy:{},", str);
        return ResponseGenerator.genSuccessResult(str.toString() + "-" + content);
    }

    @ResponseBodyEncode
    @RequestMapping("/extractRequest")
    public ResponseMessage extractRequest(@RequestHeader("Postman-Token") String token, @CookieValue("JSESSIONID") String sessionId) {
        return ResponseGenerator.genSuccessResult(token + "-" + DatePattern.NORM_DATETIME_FORMAT.format(new Date()) + "-" + sessionId);
    }

    @GetMapping("/environment")
    public ResponseMessage environment(String name) throws Exception {
        logger.info("environment:{},", name);
        logger.info("{}", url.getURL());
        return ResponseGenerator.genSuccessResult(environment.getProperty(name));
    }

    @GetMapping("/property")
    public ResponseMessage getPropertySourcesWithSpel() {
        StringBuffer sb = new StringBuffer();
        propertySources.forEach(propertySource -> {
            Object source = propertySource.getSource();
            logger.info("{}", source.toString());
            sb.append(source.toString());
        });
        return ResponseGenerator.genSuccessResult(sb.toString());
    }

    @PostMapping("/upload")
    public ResponseMessage uploadFile(@RequestPart("file") Part part) {
        logger.info("{},{},{}", part.getSubmittedFileName(), part.getSize() / 1024, part.getContentType());
        try {
            part.write("e:/" + part.getSubmittedFileName());
        } catch (IOException e) {
            return ResponseGenerator.genFailResult("上传失败");
        }
        return ResponseGenerator.genSuccessResult("上传成功");
    }
    @PostMapping("/redis")
    public ResponseMessage accessRedis(@RequestHeader("token") String token) {
        String value = jedisClient.get(token);
        if (StringUtils.isEmpty(value)) {
            return ResponseGenerator.genFailResult("验证失败");
        }
        String tokenNew = UUID.randomUUID().toString();
        jedisClient.set(tokenNew, String.valueOf(System.currentTimeMillis()), 60 * 5);
        return ResponseGenerator.genSuccessResult(tokenNew);
    }

    @PostMapping("/fileUpload")
    public ResponseMessage fileUpload(@RequestBody Map<String, String> params) throws Exception {
        logger.info("{}", params.get("fileStr"));
        byte[] fileBytes = Base64.getDecoder().decode(params.get("fileStr"));
        FileUtils.writeByteArrayToFile(new File("E:/" +params.get("fileName")), fileBytes);
        return ResponseGenerator.genSuccessResult();
    }
}
