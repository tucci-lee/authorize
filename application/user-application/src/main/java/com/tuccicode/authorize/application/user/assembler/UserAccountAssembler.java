package com.tuccicode.authorize.application.user.assembler;

import com.tuccicode.authorize.application.user.dto.vo.UserAccountVO;
import com.tuccicode.authorize.domain.user.entity.user.UserAccount;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class UserAccountAssembler {

    public static UserAccountVO toVO(UserAccount entity){
        UserAccountVO vo = new UserAccountVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
