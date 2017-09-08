package com.tangshengbo.service;


import com.tangshengbo.model.Account;

import java.util.List;

/**
 * Created by CodeGenerator on 2017/09/03.
 */
public interface AccountService extends Service<Account> {

    void saveBatchAccount(int batchCount);

    void updateBatchAccount(int batchCount);

    List<Account> findAllByType(String type);
}
