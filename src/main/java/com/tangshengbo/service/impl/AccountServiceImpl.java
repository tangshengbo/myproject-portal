package com.tangshengbo.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.tangshengbo.cache.JedisClient;
import com.tangshengbo.core.JsonUtils;
import com.tangshengbo.dao.AccountMapper;
import com.tangshengbo.exception.ServiceException;
import com.tangshengbo.model.Account;
import com.tangshengbo.service.AbstractService;
import com.tangshengbo.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * Created by CodeGenerator on 2017/09/03.
 */
@Service("accountService")
@Transactional(rollbackFor = ServiceException.class)
public class AccountServiceImpl extends AbstractService<Account> implements AccountService {

    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private static final ThreadLocal<Account> THREAD_LOCAL = new ThreadLocal<Account>();

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("REDIS_ACCOUNT")
    private String REDIS_ACCOUNT_KEY;

    @Value("#{100}")
    private Integer REDIS_ACCOUNT_EXPIRE;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private static final String ACCOUNT_JSON = "accountJson";

    @Override
    public void saveBatchAccount(int batchCount) {
        logger.info("批量插入开始...................");
        List<Account> accounts = Lists.newArrayList();
        AccountMapper mapper = sqlSessionTemplate.getMapper(AccountMapper.class);
        Random rand = new Random();
        for (int i = 0; i < batchCount; i++) {
            accounts.add(new Account("唐声波", (i * 11.3 * rand.nextInt(100000)), new Date()));

        }
        logger.info("插入总数{}", accounts.size());
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println(mapper.selectAll());
//        accounts.forEach(mapper::insertSelective);
//        userMapper.insertList(users);
//        saveBatch(accounts);
        watch.stop();
        logger.info("批量插入结束...................{}s", watch.getTotalTimeSeconds());
    }

    @Override
    public void updateBatchAccount(int batchCount) {

    }

    @Override
    public List<Account> findAllByType(String type) {
        List<Account> accounts;
        //先从缓存里面找
        String result = jedisClient.hget(REDIS_ACCOUNT_KEY, type);
        if (StringUtils.isNotBlank(result)) {
            accounts = JsonUtils.jsonToList(result, Account.class);
            return accounts;
        }
        //从数据库里查
        accounts = findAll();
        //设置到缓存
        jedisClient.hset(REDIS_ACCOUNT_KEY, type, JsonUtils.objectToJson(accounts));
        //设置超时时间
        jedisClient.expire(REDIS_ACCOUNT_KEY, REDIS_ACCOUNT_EXPIRE);
        return accounts;
    }

    @Override
    public void removeAccount() {
        Account account = THREAD_LOCAL.get();
        if (account != null) {
            THREAD_LOCAL.remove();
        }

    }

    @Override
    public Account getAccount() {
        Account account = THREAD_LOCAL.get();
        if (account == null) {
            logger.info("account == null");
            account = new Account(Thread.currentThread().getName(), 11.1, new Date());
            THREAD_LOCAL.set(account);
        }
        return account;
    }

    @Cacheable(value = "MyCache")
    @Override
    public List<Account> findAll() {
        String value = (String) redisTemplate.opsForValue().get(ACCOUNT_JSON);
        List<Account> accountList = null;
        if (Objects.isNull(value)) {
            PageHelper.startPage(1, 10);
            accountList = super.findAll();
            logger.info("MySql查询:{}", accountList);
            String accountJson = JSON.toJSONString(accountList);
            redisTemplate.opsForValue().set(ACCOUNT_JSON, accountJson, 10 * 60, TimeUnit.SECONDS);
        } else {
            logger.info("Redis查询:{}");
            accountList = JSON.parseArray(value, Account.class);
        }
        logger.info("accountList:Size:{}", accountList.size());
        return accountList;
    }
}
