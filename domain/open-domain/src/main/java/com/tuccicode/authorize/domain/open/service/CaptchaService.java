package com.tuccicode.authorize.domain.open.service;


import com.tuccicode.authorize.domain.open.entity.captcha.EmailCaptchaType;
import com.tuccicode.authorize.domain.open.entity.captcha.SmsCaptchaType;

/**
 * @author tucci.lee
 */
public interface CaptchaService {

    /**
     * 发送短信验证码
     *
     * @param type    验证码类型{@link SmsCaptchaType}
     * @param phone   手机号，不加区号为国内短信。国际手机区号自己拼接,例如+852xxxx
     * @param captcha 发送的验证码
     */
    void sms(int type, String phone, String captcha);

    /**
     * 验证短信验证码
     *
     * @param type    验证码类型{@link SmsCaptchaType}
     * @param phone   手机号
     * @param captcha 需要验证的验证码
     */
    void smsVerify(int type, String phone, String captcha);

    /**
     * 发送邮件
     *
     * @param type    验证码类型{@link EmailCaptchaType}
     * @param email   邮箱
     * @param captcha 验证码
     */
    void email(int type, String email, String captcha);

    /**
     * 验证邮箱验证码
     *
     * @param type    验证码类型{@link EmailCaptchaType}
     * @param email   邮箱
     * @param captcha 需要验证的验证码
     */
    void emailVerify(int type, String email, String captcha);
}
