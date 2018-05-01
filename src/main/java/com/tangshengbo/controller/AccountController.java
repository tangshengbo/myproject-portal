package com.tangshengbo.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.tangshengbo.core.CookieUtils;
import com.tangshengbo.core.JsonUtils;
import com.tangshengbo.core.ResponseGenerator;
import com.tangshengbo.core.ResponseMessage;
import com.tangshengbo.exception.ServiceException;
import com.tangshengbo.model.Account;
import com.tangshengbo.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by Administrator on 2016/11/16.
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {

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
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseMessage<List<Account>> getAccountAll(Model model, String name, Integer age) {
        logger.info("name:{}, age:{}", name, age);
        return ResponseGenerator.genSuccessResult(accountService.findAll());
    }

    // 本方法将处理 /courses/view?courseId=123 形式的URL
    @RequestMapping(value = "/save/{count}", method = RequestMethod.GET)
    public ResponseMessage<String> addAccount(@PathVariable("count") int count) {
        accountService.saveBatchAccount(count);
        return ResponseGenerator.genSuccessResult("批量新增成功");
    }

    @RequestMapping(value = "/save-urlencoded", method = {RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public ResponseMessage addAccountByUrlEncoded(Account account) {
        return ResponseGenerator.genSuccessResult(getStringResponseEntity(account));
    }

    @RequestMapping(value = "/save-body", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage addAccountByBody(@Valid @RequestBody Account account, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseGenerator.genFailResult(result.toString());
        }
        return ResponseGenerator.genSuccessResult(getStringResponseEntity(account));
    }

    private ResponseEntity<String> getStringResponseEntity(Account account) {
        logger.info("{}", account);
        return new ResponseEntity<>(Base64.getEncoder()
                .encodeToString(account.toString().getBytes()), HttpStatus.OK);
    }

    @RequestMapping(value = "/search/{id}", method = {RequestMethod.GET, RequestMethod.POST})
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
        return new ResponseEntity("success", HttpStatus.OK);
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
        } catch (Exception e) {
            throw new ServiceException("业务调用异常");
        }
        return ResponseGenerator.genSuccessResult();
    }

    @RequestMapping(value = "/download-file/{fileName}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseMessage downloadFile(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        InputStream is = AccountController.class.getResourceAsStream("/spring-mvc.xml");
        OutputStream os = null;
        try {
//            os = new BufferedOutputStream(response.getOutputStream());
//            os.write());
//            os.flush();
            Map<String, String> resultMap = new LinkedHashMap<>();
            resultMap.put("resp_body", Base64.getEncoder().encodeToString(IOUtils.toByteArray(is)));
            return ResponseGenerator.genSuccessResult(resultMap);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(os)) {
                IOUtils.closeQuietly(os);
            }
        }
        return null;
    }

    @RequestMapping(value = "/download-file-rest/{fileName}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<byte[]> downloadFile(@PathVariable("fileName") String fileName) throws IOException {
        InputStream is = AccountController.class.getResourceAsStream("/spring-mvc.xml");
        is = new FileInputStream("E:/writer.zip");
        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/zip");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        byte[] bytes = IOUtils.toByteArray(is);
        IOUtils.closeQuietly(is);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/sync_or_async", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseMessage syncOrAsync(String requestType) {
        logger.info("调用方式是:{}", requestType);
        ThreadUtil.sleep(10000);
        return ResponseGenerator.genSuccessResult("成功");
    }
}
