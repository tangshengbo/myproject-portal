package com.tangshengbo.controller;

import com.alibaba.fastjson.JSON;
import com.tangshengbo.core.ResponseGenerator;
import com.tangshengbo.core.extension.MyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by TangShengBo on 2019/1/6
 */
@Component("myHandlerController")
public class MyHandlerController implements MyHandler {

    private static Logger logger = LoggerFactory.getLogger(MyHandlerController.class);

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("正在处理...................{}", request.getRequestURI());
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter pw =  response.getWriter()) {
            pw.print(JSON.toJSONString(ResponseGenerator.genSuccessResult("处理完成saas")));
            pw.flush();
        }
        return null;
    }
}
