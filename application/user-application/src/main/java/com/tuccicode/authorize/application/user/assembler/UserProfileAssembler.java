package com.tuccicode.authorize.application.user.assembler;

import com.tuccicode.authorize.application.user.dto.vo.UserProfileVO;
import com.tuccicode.authorize.domain.user.entity.user.UserProfile;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

/**
 * @author tucci.lee
 */
public class UserProfileAssembler {

    public static UserProfileVO toVO(UserProfile entity, String domain, String token){
        UserProfileVO vo = new UserProfileVO()
                .setToken(token);
        BeanUtils.copyProperties(entity, vo);
        if(!StringUtils.isEmpty(vo.getAvatar())){
            vo.setAvatar(domain + vo.getAvatar());
        }
        return vo;
    }
}
