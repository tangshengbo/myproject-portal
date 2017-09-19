package com.tangshengbo.service.impl;

import com.tangshengbo.dao.AccountMapper;
import com.tangshengbo.model.Account;
import com.tangshengbo.service.AbstractService;
import com.tangshengbo.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by TangShengBo on 2017-09-07.
 */
@Service("defaultAccountService")
public class DefaultAccountServiceImpl extends AbstractService<Account> implements AccountService {

    private static Logger logger = LoggerFactory.getLogger(DefaultAccountServiceImpl.class);

    private AccountMapper accountMapper;

    @Override
    public void saveBatchAccount(int batchCount) {
        logger.info("DefaultImpl 批量插入开始.................");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("DefaultImpl 批量插入结束.................");
    }

    @Override
    public void updateBatchAccount(int batchCount) {

    }

    @Override
    public List<Account> findAllByType(String type) {
        return null;
    }

    @Override
    public void removeAccount() {

    }

    @Override
    public Account getAccount() {
        return null;
    }
}
