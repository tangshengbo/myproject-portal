package com.tangshengbo.dao;


import com.tangshengbo.core.MyMapper;
import com.tangshengbo.model.Account;

public interface AccountMapper extends MyMapper<Account> {

    /**
     *
     * @mbggenerated 2017-09-06
     */
    int insertSelective(Account record);
}