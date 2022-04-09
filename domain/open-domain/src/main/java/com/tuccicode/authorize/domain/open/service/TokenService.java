package com.tuccicode.authorize.domain.open.service;

/**
 * @author tucci.lee
 */
public interface TokenService {

    /**
     * 获取手机验证token
     *
     * @param type 类型{@link com.tuccicode.authorize.domain.open.entity.captcha.SmsCaptchaType}
     * @param uid  用户uid
     * @return token
     */
    String getPhoneVerifyToken(int type, long uid);

    /**
     * 验证手机验证token是否正确
     *
     * @param type  类型{@link com.tuccicode.authorize.domain.open.entity.captcha.SmsCaptchaType}
     * @param uid   用户uid
     * @param token token
     */
    void verifyPhoneVerifyToken(int type, Long uid, String token);
}
