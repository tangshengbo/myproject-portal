package com.tangshengbo.controller;

import com.tangshengbo.core.ResponseGenerator;
import com.tangshengbo.core.ResponseMessage;
import com.tangshengbo.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Tangshengbo on 2018/3/5.
 */
@Controller
@RequestMapping("/log")
public class LogController {

    private static Logger logger = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private LogService logService;

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
}
