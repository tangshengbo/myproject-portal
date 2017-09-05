package com.tangshengbo.controller;

import com.tangshengbo.model.Account;
import com.tangshengbo.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    private static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    // 本方法将处理 /courses/view?courseId=123 形式的URL
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Account> getAccountAll(Model model) {
        logger.info("getAccountAll start>>>>>>>>>>>>>>>>>>>>>>>>");
        logger.info("getAccountAll end>>>>>>>>>>>>>>>>>>>>>>>>");
        return accountService.findAll();
    }

    // 本方法将处理 /courses/view?courseId=123 形式的URL
    @RequestMapping(value = "/staticpage.html", method = RequestMethod.POST)
    public ResponseEntity addAccount(Model model) {
        logger.info("addAccount start>>>>>>>>>>>>>>>>>>>>>>>>");

        logger.info("addAccount end>>>>>>>>>>>>>>>>>>>>>>>>");
        return new ResponseEntity("success", HttpStatus.OK);
    }

}
