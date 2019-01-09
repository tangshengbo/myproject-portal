package com.tangshengbo.controller;

import com.tangshengbo.core.extension.MyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by TangShengBo on 2019/1/6
 */
@Component("myHandlerController")
public class MyHandlerController implements MyHandler {

    private static Logger logger = LoggerFactory.getLogger(MyHandlerController.class);

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("正在处理...................{}", request.getRequestURI());
//        response.setContentType("application/json;charset=UTF-8");
//        try (PrintWriter pw =  response.getWriter()) {
//            pw.print(JSON.toJSONString(ResponseGenerator.genSuccessResult("处理完成saas")));
//            pw.flush();
//        }
//        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
//        rd.forward(request, response);
        response.setStatus(302);
        response.setHeader("Location", request.getContextPath() +"/index.jsp");
//        response.sendRedirect(request.getContextPath() +"/index.jsp");
        return null;
    }
}
