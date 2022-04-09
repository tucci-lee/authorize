package com.tuccicode.authorize.domain.user.entity.user;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class SigninLogCreate {

    public SigninLogCreate(String username, String ip, String os, String browser, boolean status, String message) {
        this.username = username;
        this.os = os;
        this.browser = browser;
        this.ip = ip;
        this.status = status;
        this.message = message;
    }

    private String username;

    private String os;

    private String browser;

    private String ip;

    private Boolean status;

    private String message;
}
