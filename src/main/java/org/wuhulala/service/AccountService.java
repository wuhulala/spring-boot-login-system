package org.wuhulala.service;


import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wuhulala.dal.mapper.AccountMapper;
import org.wuhulala.dal.model.Account;
import org.wuhulala.util.PasswordUtil;
import org.wuhulala.util.TokenUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @author Wuhulala
 * @version 1.0
 * @updateTime 2016/12/25
 */
@Service
public class AccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    private static final String REDIS_PREFIX = "account:";

    @Resource(name="redisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    @Resource(name="valueOperations")
    private ValueOperations<String,Object> valueOperations;

    @Autowired
    private AccountMapper accountMapper;


    @Transactional
    public Account login(String name, String password, HttpServletRequest request) {
        String newPass = PasswordUtil.createPassword(password);
        Account account = new Account(name, newPass);

        account = accountMapper.login(account);

        if (null != account) {
            String token = TokenUtil.generateToken(account.getId(),request.getRemoteAddr(),request.getRequestURI(), DateUtils.addDays(new Date(),10).getTime());
            account.setToken(token);
            valueOperations.set(REDIS_PREFIX+account.getId(),token);
            LOGGER.info("用户"+account.getId()+"登录成功");
            account.setLastLogin(new Date());
            accountMapper.updateLastLogin(account);
            return account;
        }
        return null;
    }

    /**
     * 注册
     *
     * @return
     * 1 用户名已存在
     * 2 数据库错误
     * 3 注册成功
     */
    @Transactional
    public int register(String name, String password) {
        if (accountMapper.findByName(name) != null) {
            return 1;
        }
        String newPass = PasswordUtil.createPassword(password);
        Account account = new Account(name, newPass);
        accountMapper.insert(account);
        return  account.getId() == null ? 2 : 3;
    }

    /**
     * 修改密码
     *
     * @return
     * 0 用户不存在
     * 1 原密码错误
     * 2 修改成功
     */
    @Transactional
    public int editPassword(Long id, String password, String newPassword) {

        Account account = accountMapper.findById(id);
        if (null != account) {
            if(PasswordUtil.authenticatePassword(account.getPassword(),password)) return 1;
            account.setPassword(PasswordUtil.createPassword(newPassword));
            accountMapper.updatePassword(account);
            return 2;
        }
        return 0;
    }

    /**
     * 删除session
     *
     * 0 已删除或不存在
     * 1 删除成功
     */
    public int deleteSession(Long accountId){
        String key = REDIS_PREFIX.concat(String.valueOf(accountId));
        if(valueOperations.get(key) != null){
            LOGGER.info("用户"+accountId+"退出登录");
            redisTemplate.delete(key);
            return 1;
        }
        return 0;
    }
}
