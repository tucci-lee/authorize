package com.tuccicode.authorize.domain.open.service.impl;

import com.tuccicode.authorize.common.exception.AuthorizeBizCode;
import com.tuccicode.authorize.domain.open.config.properties.SmsProperties;
import com.tuccicode.authorize.domain.open.constant.OpenCacheConst;
import com.tuccicode.authorize.domain.open.constant.OpenEmailConst;
import com.tuccicode.authorize.domain.open.entity.captcha.EmailCaptchaType;
import com.tuccicode.authorize.domain.open.entity.captcha.SmsCaptchaType;
import com.tuccicode.authorize.domain.open.external.EmailService;
import com.tuccicode.authorize.domain.open.external.SmsService;
import com.tuccicode.authorize.domain.open.service.CaptchaService;
import com.tuccicode.raccoon.cache.CacheOperate;
import com.tuccicode.raccoon.exception.Assert;
import com.tuccicode.raccoon.exception.BizException;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author tucci.lee
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    private final CacheOperate cacheOperate;
    private final SmsService smsService;
    private final EmailService emailService;
    private final AsyncTaskExecutor asyncTaskExecutor;

    public CaptchaServiceImpl(CacheOperate cacheOperate,
                              SmsService smsService,
                              EmailService emailService,
                              AsyncTaskExecutor asyncTaskExecutor) {
        this.cacheOperate = cacheOperate;
        this.smsService = smsService;
        this.emailService = emailService;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    @Override
    public void sms(int type, String phone, String captcha) {
        Assert.notEmpty(phone, AuthorizeBizCode.PHONE_REQUIRED);
        Assert.notEmpty(captcha, AuthorizeBizCode.SMS_CAPTCHA_ERROR);
        String template;
        String cacheKey;

        SmsProperties properties = smsService.properties();
        switch (type) {
            case SmsCaptchaType.SIGNIN:
                // 此版本快速注册，登录注册都使用这个验证码
                template = properties.getTemplate().getSignin();
                cacheKey = OpenCacheConst.SMS_CAPTCHA_SIGNIN + phone;
                break;
            case SmsCaptchaType.UPDATE_PHONE:
                template = properties.getTemplate().getModifyPhone();
                cacheKey = OpenCacheConst.SMS_CAPTCHA_UPDATE_PHONE + phone;
                break;
            case SmsCaptchaType.UPDATE_PHONE_VERIFY:
                template = properties.getTemplate().getModifyPhoneVerify();
                cacheKey = OpenCacheConst.SMS_CAPTCHA_UPDATE_PHONE_VERIFY + phone;
                break;
            case SmsCaptchaType.UPDATE_EMAIL_VERIFY:
                template = properties.getTemplate().getModifyEmailVerify();
                cacheKey = OpenCacheConst.SMS_CAPTCHA_UPDATE_EMAIL_VERIFY + phone;
                break;
            case SmsCaptchaType.UPDATE_PASSWORD:
                template = properties.getTemplate().getModifyPassword();
                cacheKey = OpenCacheConst.SMS_CAPTCHA_UPDATE_PASSWORD + phone;
                break;
            default:
                throw new BizException(AuthorizeBizCode.CAPTCHA_TYPE_NOT_SUPPORTED);
        }

        // 缓存验证码
        cacheOperate.set(cacheKey, captcha, OpenCacheConst.CAPTCHA_TIMEOUT, TimeUnit.MINUTES);
        // 短信服务
        smsService.send(phone, properties.getSignName(), template, "{\"code\":\"" + captcha + "\"}");
    }

    @Override
    public void smsVerify(int type, String phone, String captcha) {
        Assert.notEmpty(phone, AuthorizeBizCode.SMS_CAPTCHA_ERROR);
        Assert.notEmpty(captcha, AuthorizeBizCode.SMS_CAPTCHA_ERROR);

        String cacheKey;
        switch (type) {
            case SmsCaptchaType.SIGNIN:
                // 此版本快速注册，登录注册都使用这个验证码
                cacheKey = OpenCacheConst.SMS_CAPTCHA_SIGNIN + phone;
                break;
            case SmsCaptchaType.UPDATE_PHONE:
                cacheKey = OpenCacheConst.SMS_CAPTCHA_UPDATE_PHONE + phone;
                break;
            case SmsCaptchaType.UPDATE_PHONE_VERIFY:
                cacheKey = OpenCacheConst.SMS_CAPTCHA_UPDATE_PHONE_VERIFY + phone;
                break;
            case SmsCaptchaType.UPDATE_EMAIL_VERIFY:
                cacheKey = OpenCacheConst.SMS_CAPTCHA_UPDATE_EMAIL_VERIFY + phone;
                break;
            case SmsCaptchaType.UPDATE_PASSWORD:
                cacheKey = OpenCacheConst.SMS_CAPTCHA_UPDATE_PASSWORD + phone;
                break;
            default:
                throw new BizException(AuthorizeBizCode.SMS_CAPTCHA_ERROR);
        }

        String cacheValue = cacheOperate.get(cacheKey);
        Assert.isTrue(captcha.equals(cacheValue), AuthorizeBizCode.SMS_CAPTCHA_ERROR);
        cacheOperate.delete(cacheKey);
    }

    @Override
    public void email(int type, String email, String captcha) {
        Assert.notEmpty(email, AuthorizeBizCode.EMAIL_REQUIRED);
        Assert.notEmpty(captcha, AuthorizeBizCode.EMAIL_CAPTCHA_ERROR);

        String subject;
        String template;
        String cacheKey;
        switch (type) {
            case EmailCaptchaType.UPDATE_EMAIL:
                subject = OpenEmailConst.UPDATE_EMAIL_SUBJECT;
                template = String.format(OpenEmailConst.UPDATE_EMAIL_TEMPLATE, captcha);
                cacheKey = OpenCacheConst.EMAIL_CAPTCHA_UPDATE_EMAIL + email;
                break;
            default:
                throw new BizException(AuthorizeBizCode.CAPTCHA_TYPE_NOT_SUPPORTED);
        }

        // 缓存验证码
        cacheOperate.set(cacheKey, captcha, OpenCacheConst.CAPTCHA_TIMEOUT, TimeUnit.MINUTES);
        // 邮箱服务
        asyncTaskExecutor.execute(() -> emailService.send(email, subject, template));
    }

    @Override
    public void emailVerify(int type, String email, String captcha) {
        Assert.notEmpty(email, AuthorizeBizCode.EMAIL_REQUIRED);
        Assert.notEmpty(captcha, AuthorizeBizCode.EMAIL_CAPTCHA_ERROR);

        String cacheKey;
        switch (type) {
            case EmailCaptchaType.UPDATE_EMAIL:
                cacheKey = OpenCacheConst.EMAIL_CAPTCHA_UPDATE_EMAIL + email;
                break;
            default:
                throw new BizException(AuthorizeBizCode.EMAIL_CAPTCHA_ERROR);
        }

        String cacheValue = cacheOperate.get(cacheKey);
        Assert.isTrue(captcha.equals(cacheValue), AuthorizeBizCode.EMAIL_CAPTCHA_ERROR);
        cacheOperate.delete(cacheKey);
    }
}
