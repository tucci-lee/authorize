package com.tuccicode.authorize.domain.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuccicode.authorize.domain.user.db.UserAccountDO;

/**
 * @author tucci.lee
 */
public interface UserAccountMapper extends BaseMapper<UserAccountDO> {

    UserAccountDO selectByUid(Long uid);

    UserAccountDO selectByPhone(String phone);

    UserAccountDO selectByEmail(String email);

    int updateByUid(UserAccountDO updateAccount);

}
