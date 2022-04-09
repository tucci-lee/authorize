package com.tuccicode.authorize.domain.user.entity.user;

import com.tuccicode.authorize.common.exception.AuthorizeBizCode;
import com.tuccicode.raccoon.exception.Assert;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class UserCreate {

    private String phone;

    public UserCreate(String phone) {
        Assert.notEmpty(phone, AuthorizeBizCode.PHONE_REQUIRED);
        this.phone = phone;
    }
}
