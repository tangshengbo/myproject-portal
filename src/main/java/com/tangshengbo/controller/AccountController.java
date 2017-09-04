package com.tangshengbo.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2016/11/16.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    private Log log = LogFactory.getLog(this.getClass());



    // 本方法将处理 /courses/view?courseId=123 形式的URL
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String getAccountAll(Model model) {
        log.info("getAccountAll start>>>>>>>>>>>>>>>>>>>>>>>>");

        for (int i = 0; i < 10; i++) {
            log.info("getAccountAll start>>>>>>>>>>>>>>>>>>>>>>>>");
        }



        log.info("getAccountAll end>>>>>>>>>>>>>>>>>>>>>>>>");
        return "account_list";
    }

    // 本方法将处理 /courses/view?courseId=123 形式的URL
    @RequestMapping(value = "/staticpage.html", method = RequestMethod.POST)
    public ResponseEntity addAccount(Model model) {
        log.info("addAccount start>>>>>>>>>>>>>>>>>>>>>>>>");

        log.info("addAccount end>>>>>>>>>>>>>>>>>>>>>>>>");
        return new ResponseEntity("success", HttpStatus.OK);
    }

}
