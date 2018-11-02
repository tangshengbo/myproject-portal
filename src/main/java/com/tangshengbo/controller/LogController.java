package com.tangshengbo.controller;

import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;
import com.tangshengbo.core.ResponseGenerator;
import com.tangshengbo.core.ResponseMessage;
import com.tangshengbo.model.CanvasImage;
import com.tangshengbo.model.HttpLog;
import com.tangshengbo.model.LoveImage;
import com.tangshengbo.model.MyInject;
import com.tangshengbo.service.AccountService;
import com.tangshengbo.service.HttpLogService;
import com.tangshengbo.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
        loveImage.setCreateDate(new Date());
        loveImage.setImgUrl("http://");
        loveImage.setId(commonSelfIdGenerator.generateId().intValue());
        return ResponseGenerator.genSuccessResult(loveImage);
    }
}
