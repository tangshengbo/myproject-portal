package com.tangshengbo.service.impl;

import com.google.common.collect.Lists;
import com.tangshengbo.dao.AccountMapper;
import com.tangshengbo.model.Account;
import com.tangshengbo.service.AbstractService;
import com.tangshengbo.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2017/09/03.
 */
@Service("accountService")
public class AccountServiceImpl extends AbstractService<Account> implements AccountService {

    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void saveBatchAccount(int batchCount) {
        logger.info("批量插入开始...................");
        List<Account> accounts = Lists.newArrayList();
        for (int i = 0; i < batchCount; i++) {
            accounts.add(new Account("唐声波", (i * 11.3), new Date()));
        }
        logger.info("插入总数{}", accounts.size());
//        userMapper.insertList(users);
        saveBatch(accounts);
        logger.info("批量插入结束...................");
    }

    @Override
    public void updateBatchAccount(int batchCount) {

    }
}
