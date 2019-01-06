package com.tangshengbo.core.extension;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by TangShengBo on 2019/1/6
 */
public interface MyHandler {

    ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
