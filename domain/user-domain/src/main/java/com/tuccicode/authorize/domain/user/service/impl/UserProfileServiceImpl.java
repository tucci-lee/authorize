package com.tuccicode.authorize.domain.user.service.impl;

import com.tuccicode.authorize.domain.user.convertor.UserConvertor;
import com.tuccicode.authorize.domain.user.db.UserProfileDO;
import com.tuccicode.authorize.domain.user.entity.user.UserProfile;
import com.tuccicode.authorize.domain.user.entity.user.UserProfileUpdate;
import com.tuccicode.authorize.domain.user.mapper.UserProfileMapper;
import com.tuccicode.authorize.domain.user.service.UserProfileService;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileMapper userProfileMapper;

    public UserProfileServiceImpl(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public UserProfile getByUid(Long uid) {
        UserProfileDO userProfileDO = userProfileMapper.selectByUid(uid);
        return UserConvertor.toProfileEntity(userProfileDO);
    }

    @Override
    public void updateByUid(UserProfileUpdate profile) {
        UserProfileDO updateProfile = UserConvertor.toProfileUpdateDO(profile);
        userProfileMapper.updateByUid(updateProfile);
    }
}
