package com.tuccicode.authorize.domain.user.entity.user;

import com.tuccicode.authorize.common.exception.AuthorizeBizCode;
import com.tuccicode.raccoon.exception.Assert;
import lombok.Data;

/**
 * @author tucci.lee
 */
@Data
public class UserAccountEmailUpdate {
    private Long uid;
    private String email;

    public UserAccountEmailUpdate(Long uid, String email) {
        Assert.notNull(uid, AuthorizeBizCode.PARAMETER_ERROR);
        Assert.notEmpty(email, AuthorizeBizCode.EMAIL_REQUIRED);
        this.uid = uid;
        this.email = email;
    }
}
