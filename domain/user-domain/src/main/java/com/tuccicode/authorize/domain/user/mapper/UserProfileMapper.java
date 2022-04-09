package com.tuccicode.authorize.domain.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuccicode.authorize.domain.user.db.UserProfileDO;

/**
 * @author tucci.lee
 */
public interface UserProfileMapper extends BaseMapper<UserProfileDO> {

    UserProfileDO selectByUid(Long uid);

    int updateByUid(UserProfileDO updateProfile);
}
