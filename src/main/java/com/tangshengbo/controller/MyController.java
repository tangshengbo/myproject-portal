package com.tangshengbo.controller;

import com.tangshengbo.model.HttpLog;
import com.tangshengbo.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by TangShengBo on 2018/12/22
 */
public class MyController extends AbstractController implements Controller {

    private static Logger logger = LoggerFactory.getLogger(MyController.class);

    @Autowired
    private LogService logService;

//    @Override
//    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("处理请求开始...............");
        List<HttpLog> httpLogs = logService.listHttpLog();
        return new ModelAndView("log_list", "collects", httpLogs);
    }
}
