package com.tangshengbo.controller;

import com.tangshengbo.core.ResponseGenerator;
import com.tangshengbo.core.ResponseMessage;
import com.tangshengbo.service.HttpLogService;
import com.tangshengbo.model.HttpLog;
import com.tangshengbo.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by Tangshengbo on 2018/3/5.
 */
@Controller
@RequestMapping("/log")
public class LogController {

    private static Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogService logService;

    @Autowired
    private HttpLogService dao;

    // 本方法将处理 /courses/view?courseId=123 形式的URL
    @ResponseBody
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage getHttpLogs() {
        return ResponseGenerator.genSuccessResult(logService.listHttpLog());
    }

    @ResponseBody
    @RequestMapping(value = "/code", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage genVerificationCode() {
        return ResponseGenerator.genSuccessResult((int) (Math.random() * 900000) + 100000);
    }

    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseMessage addHttpLog(@RequestBody HttpLog log) {
        logger.info("addHttpLog:{}", log);
        dao.saveUser(log);
        return ResponseGenerator.genSuccessResult(log);
    }

    @RequestMapping(value = "/find", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseMessage selectHttpLogs(String clientIp) {
        logger.info("selectHttpLogs:{}", clientIp);
        return ResponseGenerator.genSuccessResult(dao.findHttpLogs(clientIp));
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseMessage updateHttpLog(@RequestBody HttpLog log) {
        logger.info("updateHttpLog:{}", log);
        dao.updateHttpLog(log);
        return ResponseGenerator.genSuccessResult(log);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseMessage delete(int id) {
        logger.info("updateHttpLog:{}", log);
        dao.remvoeHttpById(id);
        return ResponseGenerator.genSuccessResult(id);
    }
}
