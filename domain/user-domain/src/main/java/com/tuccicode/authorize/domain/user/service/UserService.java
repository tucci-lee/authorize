package com.tuccicode.authorize.domain.user.service;

import com.tuccicode.authorize.domain.user.entity.user.UserCreate;

/**
 * @author tucci.lee
 */
public interface UserService {

    /**
     * 创建用户
     *
     * @param user 用户信息
     */
    Long create(UserCreate user);
}
