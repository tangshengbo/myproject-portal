package com.tangshengbo.controller;

import com.google.common.collect.Lists;
import com.tangshengbo.model.Account;
import com.tangshengbo.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Qualifier("defaultAccountService")
    private AccountService accountService;

    // 本方法将处理 /courses/view?courseId=123 形式的URL
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Account> getAccountAll(Model model) {
        return accountService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/save/{count}", method = RequestMethod.GET)
    public List<Account> saveAccount(@PathVariable("count") int count) {
        accountService.saveBatchAccount(count);
        return Lists.newArrayList();
    }
    // 本方法将处理 /courses/view?courseId=123 形式的URL
    @RequestMapping(value = "/staticpage.html", method = RequestMethod.POST)
    public ResponseEntity addAccount(Model model) {
        return new ResponseEntity("success", HttpStatus.OK);
    }

}
