package com.tangshengbo.service;

import com.tangshengbo.javaconfig.ApplicationConfig;
import com.tangshengbo.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Tangshengbo on 2018/1/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
public class AccountServiceTest {

    private static Logger logger = LoggerFactory.getLogger(AccountServiceTest.class);

    @Autowired
    private AccountService accountService;

    @Test
    public void testGetAccount() {
        List<Account> accountList = accountService.findAllByType("TT");
        accountList.forEach(account -> logger.info("Account:{}", account));
    }

    @Test
    public void testDeleteAccount() {
        accountService.deleteById(101);
    }
}
