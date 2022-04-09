package com.tuccicode.authorize.domain.user.service.impl;

import com.tuccicode.authorize.common.exception.AuthorizeBizCode;
import com.tuccicode.authorize.domain.user.constant.UserLockConst;
import com.tuccicode.authorize.domain.user.convertor.UserConvertor;
import com.tuccicode.authorize.domain.user.db.UserAccountDO;
import com.tuccicode.authorize.domain.user.db.UserProfileDO;
import com.tuccicode.authorize.domain.user.entity.user.UserCreate;
import com.tuccicode.authorize.domain.user.mapper.UserAccountMapper;
import com.tuccicode.authorize.domain.user.mapper.UserProfileMapper;
import com.tuccicode.authorize.domain.user.service.UserService;
import com.tuccicode.raccoon.exception.Assert;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class UserServiceImpl implements UserService {

    private final RedissonClient redissonClient;
    private final UserAccountMapper userAccountMapper;
    private final UserProfileMapper userProfileMapper;

    public UserServiceImpl(RedissonClient redissonClient,
                           UserAccountMapper userAccountMapper,
                           UserProfileMapper userProfileMapper) {
        this.redissonClient = redissonClient;
        this.userAccountMapper = userAccountMapper;
        this.userProfileMapper = userProfileMapper;
    }

    /**
     * 1. 锁住用户手机号
     * 2. 检查用户是否存在
     * 3. 创建用户账号信息
     * 4. 创建用户资料信息
     *
     * @param user 用户信息
     * @return uid
     */
    @Override
    public Long create(UserCreate user) {
        UserAccountDO createAccount = UserConvertor.toAccountCreateDO(user);

        RLock lock = redissonClient.getLock(UserLockConst.USER_ACCOUNT_PHONE + user.getPhone());
        lock.lock();
        try {
            UserAccountDO queryAccount = userAccountMapper.selectByPhone(user.getPhone());
            Assert.isNull(queryAccount, AuthorizeBizCode.PHONE_EXIST);
            userAccountMapper.insert(createAccount);
        } finally {
            lock.unlock();
        }

        UserProfileDO createProfile = UserConvertor.toProfileCreateDO(createAccount.getUid());
        userProfileMapper.insert(createProfile);

        return createAccount.getUid();
    }
}
