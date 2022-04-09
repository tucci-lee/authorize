package com.tuccicode.authorize.domain.open.entity.captcha;

/**
 * @author tucci.lee
 */
public class SmsCaptchaType {
    /**
     * 1xx用户输入手机
     * 102：登录/注册
     * 103：修改手机
     *
     * 2xx根据用户设置的手机发送验证码
     * 201：修改手机校验（修改手机号之前，给现在绑定的手机发送验证码）
     * 202：修改邮箱校验（修改邮箱之前，给现在绑定的手机发送验证码）
     * 203：修改密码
     */
    public static final int SIGNIN = 102;
    public static final int UPDATE_PHONE = 103;

    public static final int UPDATE_PHONE_VERIFY = 201;
    public static final int UPDATE_EMAIL_VERIFY = 202;
    public static final int UPDATE_PASSWORD = 203;
}
