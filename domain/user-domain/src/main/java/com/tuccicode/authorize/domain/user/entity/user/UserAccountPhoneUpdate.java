package com.tuccicode.authorize.domain.user.entity.user;

import com.tuccicode.authorize.common.exception.AuthorizeBizCode;
import com.tuccicode.raccoon.exception.Assert;
import lombok.Data;

/**
 * @author tucci.lee
 */
@Data
public class UserAccountPhoneUpdate {
    private Long uid;
    private String phone;

    public UserAccountPhoneUpdate(Long uid, String phone) {
        Assert.notNull(uid, AuthorizeBizCode.PARAMETER_ERROR);
        Assert.notEmpty(phone, AuthorizeBizCode.PHONE_REQUIRED);
        this.uid = uid;
        this.phone = phone;
    }
}
