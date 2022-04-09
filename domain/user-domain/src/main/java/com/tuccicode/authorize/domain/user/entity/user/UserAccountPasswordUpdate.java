package com.tuccicode.authorize.domain.user.entity.user;

import com.tuccicode.authorize.common.exception.AuthorizeBizCode;
import com.tuccicode.raccoon.exception.Assert;
import lombok.Data;

/**
 * @author tucci.lee
 */
@Data
public class UserAccountPasswordUpdate {

    private Long uid;

    private String password;

    public UserAccountPasswordUpdate(Long uid, String password) {
        Assert.notNull(uid, AuthorizeBizCode.PARAMETER_ERROR);
        Assert.notEmpty(password, AuthorizeBizCode.PASSWORD_REQUIRED);
        this.uid = uid;
        this.password = password;
    }
}
