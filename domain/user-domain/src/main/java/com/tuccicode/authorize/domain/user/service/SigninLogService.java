package com.tuccicode.authorize.domain.user.service;

import com.tuccicode.authorize.domain.user.entity.user.SigninLogCreate;

/**
 * @author tucci.lee
 */
public interface SigninLogService {

    void create(SigninLogCreate log);
}
