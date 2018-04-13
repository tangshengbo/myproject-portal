package com.tangshengbo.service;

/**
 * Created by Tangshengbo on 2017/10/30.
 * 全局异常处理，捕获所有Controller中抛出的异常
 */


import com.tangshengbo.core.ResponseGenerator;
import com.tangshengbo.core.ResponseMessage;
import com.tangshengbo.exception.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    //处理自定义的异常
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseMessage customHandler(ServiceException e) {
        return ResponseGenerator.genFailResult(e.getMessage());
    }

    //其他未处理的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseMessage exceptionHandler(Exception e) {
        return ResponseGenerator.genServerErrorResult("服务器内部错误");
    }
}

