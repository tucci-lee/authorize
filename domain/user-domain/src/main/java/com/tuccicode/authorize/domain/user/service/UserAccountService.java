package com.tuccicode.authorize.domain.user.service;

import com.tuccicode.authorize.domain.user.entity.user.UserAccountEmailUpdate;
import com.tuccicode.authorize.domain.user.entity.user.UserAccountPasswordUpdate;
import com.tuccicode.authorize.domain.user.entity.user.UserAccount;
import com.tuccicode.authorize.domain.user.entity.user.UserAccountPhoneUpdate;

/**
 * @author tucci.lee
 */
public interface UserAccountService {

    /**
     * 根据uid查询账号
     *
     * @param uid uid
     * @return 账号
     */
    UserAccount getByUid(Long uid);

    /**
     * 根据手机号查询账号
     *
     * @param phone 手机
     * @return 账号
     */
    UserAccount getByPhone(String phone);

    /**
     * 根据邮箱查询账号
     *
     * @param email 邮箱
     * @return 账号
     */
    UserAccount getByEmail(String email);

    /**
     * 密码验证
     *
     * @param plaintext  明文
     * @param ciphertext 密文
     * @return 是否相同
     */
    boolean passwordVerify(String plaintext, String ciphertext);

    /**
     * 数据脱敏
     *
     * @param userAccount 账号信息
     */
    void desensitization(UserAccount userAccount);

    /**
     * 修改密码
     *
     * @param update 密码信息
     */
    void updatePasswordByUid(UserAccountPasswordUpdate update);

    /**
     * 修改手机
     *
     * @param update 手机信息
     */
    void updatePhoneByUid(UserAccountPhoneUpdate update);

    /**
     * 修改邮箱
     *
     * @param update 邮箱信息
     */
    void updateEmailByUid(UserAccountEmailUpdate update);
}
