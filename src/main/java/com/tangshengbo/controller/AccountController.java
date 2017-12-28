package com.tangshengbo.controller;

import com.tangshengbo.core.CookieUtils;
import com.tangshengbo.core.JsonUtils;
import com.tangshengbo.core.ResponseGenerator;
import com.tangshengbo.core.ResponseMessage;
import com.tangshengbo.exception.ServiceException;
import com.tangshengbo.model.Account;
import com.tangshengbo.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

    @Value("${CAT_COOKIE_EXPIRE}")
    private Integer CAT_COOKIE_EXPIRE;

    @Value("${CAT_COOKIE_NAME}")
    private String CAT_COOKIE_NAME;

    @Value("${COOKIE_ENCODING}")
    private String COOKIE_ENCODING;

    // 本方法将处理 /courses/view?courseId=123 形式的URL
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Account> getAccountAll(Model model) {
        return accountService.findAll();
    }

    // 本方法将处理 /courses/view?courseId=123 形式的URL
    @RequestMapping(value = "/save/{count}", method = RequestMethod.GET)
    public ResponseEntity addAccount(@PathVariable("count") int count) {
        accountService.saveBatchAccount(count);
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> search(@PathVariable("id") int id, HttpServletRequest request, HttpServletResponse response) {
        List<Account> accounts = getItemListFromCart(request);
        for (Account account : accounts) {
            if (account.getId() == id) {
                return new ResponseEntity(account, HttpStatus.OK);
            }
        }
        Account account = accountService.findById(id);
        accounts.add(account);
        // 4、把list序列号写入cookie，进行编码。
        CookieUtils.setCookie(request, response, CAT_COOKIE_NAME, JsonUtils.objectToJson(accounts), CAT_COOKIE_EXPIRE,
                COOKIE_ENCODING);
        return new ResponseEntity(account, HttpStatus.OK);
    }

    @RequestMapping(value = "/search-all/{type}", method = RequestMethod.GET)
    public ResponseEntity<List<Account>> searchAccounts(@PathVariable("type") String type, HttpServletRequest request, HttpServletResponse response) {
        List<Account> accounts = accountService.findAllByType(type);
        return new ResponseEntity(accounts, HttpStatus.OK);
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据ThreadLocal")
    @RequestMapping(value = "/search-thread-local", method = RequestMethod.GET)
    public ResponseEntity<Account> searchByThreadLocal() {
        return new ResponseEntity(accountService.getAccount(), HttpStatus.OK);
    }

    @RequestMapping(value = "/remove-thread-local", method = RequestMethod.GET)
    public ResponseEntity removeByThreadLocal() {
        accountService.removeAccount();
        return new ResponseEntity("success",HttpStatus.OK);
    }

    /**
     * 取购物车信息
     */
    private List<Account> getItemListFromCart(HttpServletRequest request) {
        //从cookie中取商品列表
        String string = CookieUtils.getCookieValue(request, CAT_COOKIE_NAME, COOKIE_ENCODING);
        try {
            if (StringUtils.isEmpty(string)) {
                return new ArrayList<>();
            }
            List<Account> list = JsonUtils.jsonToList(string, Account.class);
            if (list == null) {
                return new ArrayList<>();
            }
            return list;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }


    @ApiOperation(value = "异常测试")
    @RequestMapping(value = "/exception-check", method = RequestMethod.GET)
    public ResponseMessage exceptionHandler() {

        try {
            int i = 100 / 0;
        }  catch (Exception e) {
            throw new ServiceException("业务调用异常");
        }
        return ResponseGenerator.genSuccessResult();
    }
}
