package com.tuccicode.authorize.domain.user.service;

import com.tuccicode.authorize.domain.user.entity.user.UserProfile;
import com.tuccicode.authorize.domain.user.entity.user.UserProfileUpdate;

/**
 * @author tucci.lee
 */
public interface UserProfileService {

    /**
     * 根据uid查询用户信息
     *
     * @param uid uid
     * @return UserProfile
     */
    UserProfile getByUid(Long uid);

    /**
     * 修改用户信息
     *
     * @param profile 用户信息
     */
    void updateByUid(UserProfileUpdate profile);
}
