package com.tuccicode.authorize.domain.user.convertor;

import com.tuccicode.authorize.domain.user.db.SigninLogDO;
import com.tuccicode.authorize.domain.user.entity.user.SigninLogCreate;

/**
 * @author tucci.lee
 */
public class SigninLogConvertor {

    public static SigninLogDO toCreateDO(SigninLogCreate entity){
        return new SigninLogDO()
                .setUsername(entity.getUsername())
                .setOs(entity.getOs())
                .setBrowser(entity.getBrowser())
                .setIp(entity.getIp())
                .setStatus(entity.getStatus())
                .setMessage(entity.getMessage())
                .setCreateTime(System.currentTimeMillis());
    }
}
