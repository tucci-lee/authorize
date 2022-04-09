package com.tuccicode.authorize.domain.user.service.impl;

import com.tuccicode.authorize.common.exception.AuthorizeBizCode;
import com.tuccicode.authorize.domain.user.constant.UserLockConst;
import com.tuccicode.authorize.domain.user.convertor.UserConvertor;
import com.tuccicode.authorize.domain.user.db.UserAccountDO;
import com.tuccicode.authorize.domain.user.entity.user.UserAccount;
import com.tuccicode.authorize.domain.user.entity.user.UserAccountEmailUpdate;
import com.tuccicode.authorize.domain.user.entity.user.UserAccountPasswordUpdate;
import com.tuccicode.authorize.domain.user.entity.user.UserAccountPhoneUpdate;
import com.tuccicode.authorize.domain.user.mapper.UserAccountMapper;
import com.tuccicode.authorize.domain.user.service.UserAccountService;
import com.tuccicode.raccoon.exception.Assert;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {
    /**
     * 脱敏正则表达式
     */
    String EMAIL_REGEX = "^(\\w).*(@.*)$";
    String PHONE_REGEX = "^(\\d{3})\\d.*(\\d{4})$";
    /**
     * 脱敏格式
     */
    String DESENSITIZATION_FORMAT = "$1****$2";

    private final UserAccountMapper userAccountMapper;
    private final RedissonClient redissonClient;

    public UserAccountServiceImpl(UserAccountMapper userAccountMapper,
                                  RedissonClient redissonClient) {
        this.userAccountMapper = userAccountMapper;
        this.redissonClient = redissonClient;
    }

    @Override
    public UserAccount getByUid(Long uid) {
        UserAccountDO userAccountDO = userAccountMapper.selectByUid(uid);
        return UserConvertor.toAccountEntity(userAccountDO);
    }

    @Override
    public UserAccount getByPhone(String phone) {
        UserAccountDO userAccountDO = userAccountMapper.selectByPhone(phone);
        return UserConvertor.toAccountEntity(userAccountDO);
    }

    @Override
    public UserAccount getByEmail(String email) {
        UserAccountDO userAccountDO = userAccountMapper.selectByEmail(email);
        return UserConvertor.toAccountEntity(userAccountDO);
    }

    @Override
    public boolean passwordVerify(String plaintext, String ciphertext) {
        try {
            return BCrypt.checkpw(plaintext, ciphertext);
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public void desensitization(UserAccount userAccount) {
        userAccount.setPassword("******");

        String email = userAccount.getEmail();
        if (email != null) {
            String newEmail = email.replaceAll(EMAIL_REGEX, DESENSITIZATION_FORMAT);
            userAccount.setEmail(newEmail);
        }
        String phone = userAccount.getPhone();
        if (phone != null) {
            String newPhone = phone.replaceAll(PHONE_REGEX, DESENSITIZATION_FORMAT);
            userAccount.setPhone(newPhone);
        }
    }


    @Override
    public void updatePasswordByUid(UserAccountPasswordUpdate update) {
        UserAccountDO userAccountDO = UserConvertor.toAccountPasswordUpdateDO(update);
        userAccountMapper.updateByUid(userAccountDO);
    }

    @Override
    public void updatePhoneByUid(UserAccountPhoneUpdate update) {
        UserAccountDO userAccountDO = UserConvertor.toAccountPhoneUpdateDO(update);
        RLock lock = redissonClient.getLock(UserLockConst.USER_ACCOUNT_PHONE + userAccountDO.getPhone());
        lock.lock();
        try {
            UserAccountDO queryAccount = userAccountMapper.selectByPhone(userAccountDO.getPhone());
            Assert.isNull(queryAccount, AuthorizeBizCode.PHONE_EXIST);
            userAccountMapper.updateByUid(userAccountDO);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void updateEmailByUid(UserAccountEmailUpdate update) {
        UserAccountDO userAccountDO = UserConvertor.toAccountEmailUpdateDO(update);
        RLock lock = redissonClient.getLock(UserLockConst.USER_ACCOUNT_EMAIL + userAccountDO.getEmail());
        lock.lock();
        try {
            UserAccountDO queryAccount = userAccountMapper.selectByEmail(userAccountDO.getEmail());
            Assert.isNull(queryAccount, AuthorizeBizCode.EMAIL_EXIST);
            userAccountMapper.updateByUid(userAccountDO);
        } finally {
            lock.unlock();
        }
    }
}
