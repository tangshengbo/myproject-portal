package com.tangshengbo.service;

/**
 * Created by Tangshengbo on 2017/10/30.
 * 全局异常处理，捕获所有Controller中抛出的异常
 */


import com.tangshengbo.core.ResponseGenerator;
import com.tangshengbo.core.ResponseMessage;
import com.tangshengbo.exception.ServiceException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //处理自定义的异常
    @ExceptionHandler(ServiceException.class)
    public ResponseMessage customHandler(ServiceException e) {
        return ResponseGenerator.genFailResult(e.getMessage());
    }

    //其他未处理的异常
    @ExceptionHandler(Exception.class)
    public ResponseMessage exceptionHandler(Exception e) {
        logger.error("{}", ExceptionUtils.getStackTrace(e));
        return ResponseGenerator.genServerErrorResult(ExceptionUtils.getMessage(e));
    }
}

