package com.tuccicode.authorize.domain.open.service.impl;

import com.tuccicode.authorize.common.exception.AuthorizeBizCode;
import com.tuccicode.authorize.domain.open.constant.OpenCacheConst;
import com.tuccicode.authorize.domain.open.entity.captcha.SmsCaptchaType;
import com.tuccicode.authorize.domain.open.service.TokenService;
import com.tuccicode.raccoon.cache.CacheOperate;
import com.tuccicode.raccoon.exception.Assert;
import com.tuccicode.raccoon.exception.BizException;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author tucci.lee
 */
@Service
public class TokenServiceImpl implements TokenService {

    private final CacheOperate cacheOperate;

    public TokenServiceImpl(CacheOperate cacheOperate) {
        this.cacheOperate = cacheOperate;
    }

    @Override
    public String getPhoneVerifyToken(int type, long uid) {
        String token = UUID.randomUUID().toString();
        String cacheKey;
        switch (type) {
            case SmsCaptchaType.UPDATE_PHONE_VERIFY:
                cacheKey = OpenCacheConst.TOKEN_UPDATE_PHONE + token;
                break;
            case SmsCaptchaType.UPDATE_EMAIL_VERIFY:
                cacheKey = OpenCacheConst.TOKEN_UPDATE_EMAIL + token;
                break;
            default:
                throw new BizException(AuthorizeBizCode.CAPTCHA_TYPE_NOT_SUPPORTED);
        }
        cacheOperate.set(cacheKey, uid, OpenCacheConst.TOKEN_TIMEOUT, TimeUnit.MINUTES);
        return token;
    }

    @Override
    public void verifyPhoneVerifyToken(int type, Long uid, String token) {
        String cacheKey;
        switch (type) {
            case SmsCaptchaType.UPDATE_PHONE_VERIFY:
                cacheKey = OpenCacheConst.TOKEN_UPDATE_PHONE + token;
                break;
            case SmsCaptchaType.UPDATE_EMAIL_VERIFY:
                cacheKey = OpenCacheConst.TOKEN_UPDATE_EMAIL + token;
                break;
            default:
                throw new BizException(AuthorizeBizCode.CAPTCHA_TYPE_NOT_SUPPORTED);
        }

        Long cacheUid = cacheOperate.get(cacheKey);
        Assert.isTrue(uid.equals(cacheUid), AuthorizeBizCode.TOKEN_VERIFY_ERROR);
        cacheOperate.delete(cacheKey);
    }
}
