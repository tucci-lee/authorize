package com.tuccicode.authorize.domain.open.constant;

/**
 * @author tucci.lee
 */
public class OpenCacheConst {
    /**
     * 验证码过期时间，单位分钟
     */
    public static final int CAPTCHA_TIMEOUT = 5;

    /**
     * 短信验证码
     */
    public static final String SMS_CAPTCHA_SIGNIN = "captcha:sms:signin:";
    public static final String SMS_CAPTCHA_UPDATE_PHONE = "captcha:sms:update:phone:";
    public static final String SMS_CAPTCHA_UPDATE_PHONE_VERIFY = "captcha:sms:update:phone:verify:";
    public static final String SMS_CAPTCHA_UPDATE_EMAIL_VERIFY = "captcha:sms:update:email:verify:";
    public static final String SMS_CAPTCHA_UPDATE_PASSWORD = "captcha:sms:update:password:";

    /**
     * 邮箱验证码
     */
    public static final String EMAIL_CAPTCHA_UPDATE_EMAIL = "captcha:email:update:email:";

    /**
     * 数据缓存
     */
    public static final String REGION = "data:region";

    /**
     * 修改手机，邮箱的token
     */
    public static final String TOKEN_UPDATE_PHONE = "token:update:phone:";
    public static final String TOKEN_UPDATE_EMAIL = "token:update:email:";
    public static final int TOKEN_TIMEOUT = 30;


}
