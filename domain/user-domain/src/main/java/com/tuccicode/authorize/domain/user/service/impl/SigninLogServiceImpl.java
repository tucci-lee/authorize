package com.tuccicode.authorize.domain.user.service.impl;

import com.tuccicode.authorize.domain.user.convertor.SigninLogConvertor;
import com.tuccicode.authorize.domain.user.db.SigninLogDO;
import com.tuccicode.authorize.domain.user.entity.user.SigninLogCreate;
import com.tuccicode.authorize.domain.user.mapper.SigninLogMapper;
import com.tuccicode.authorize.domain.user.service.SigninLogService;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class SigninLogServiceImpl implements SigninLogService {

    private final SigninLogMapper signinLogMapper;

    public SigninLogServiceImpl(SigninLogMapper signinLogMapper) {
        this.signinLogMapper = signinLogMapper;
    }

    @Override
    public void create(SigninLogCreate log) {
        SigninLogDO signinLogDO = SigninLogConvertor.toCreateDO(log);
        signinLogMapper.insert(signinLogDO);
    }
}
