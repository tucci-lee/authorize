package com.tuccicode.authorize.application.open.service;

import com.tuccicode.authorize.application.open.dto.body.EmailCaptchaBody;
import com.tuccicode.authorize.application.open.dto.body.SmsCaptchaBody;
import com.tuccicode.authorize.common.exception.AuthorizeBizCode;
import com.tuccicode.authorize.domain.open.entity.captcha.EmailCaptchaType;
import com.tuccicode.authorize.domain.open.entity.captcha.SmsCaptchaType;
import com.tuccicode.authorize.domain.open.service.CaptchaService;
import com.tuccicode.authorize.domain.open.util.RandomUtils;
import com.tuccicode.authorize.domain.user.entity.user.UserAccount;
import com.tuccicode.authorize.domain.user.service.UserAccountService;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.raccoon.exception.Assert;
import com.tuccicode.raccoon.exception.BizException;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class CaptchaAppService {

    private final CaptchaService captchaService;
    private final UserAccountService userAccountService;
    private final AsyncTaskExecutor asyncTaskExecutor;

    public CaptchaAppService(CaptchaService captchaService,
                             UserAccountService userAccountService,
                             AsyncTaskExecutor asyncTaskExecutor) {
        this.captchaService = captchaService;
        this.userAccountService = userAccountService;
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    /**
     * 1. 校验手机号是否可以发送短信
     * 2. 除登录/注册，修改手机类型的验证码其他抛出异常
     *
     * @param body 请求体
     * @return Response
     */
    public Response sendSmsCaptcha(SmsCaptchaBody body) {
        UserAccount account = userAccountService.getByPhone(body.getPhone());
        switch (body.getType()) {
            case SmsCaptchaType.UPDATE_PHONE:
                Assert.isNull(account, AuthorizeBizCode.PHONE_EXIST);
                break;
            case SmsCaptchaType.SIGNIN:
                break;
            default:
                throw new BizException(AuthorizeBizCode.CAPTCHA_TYPE_NOT_SUPPORTED);
        }
        String captcha = RandomUtils.randomInt(4);
        captchaService.sms(body.getType(), body.getPhone(), captcha);
        return Response.success();
    }

    public Response sendEmailCaptcha(EmailCaptchaBody body) {
        UserAccount account = userAccountService.getByEmail(body.getEmail());
        switch (body.getType()) {
            case EmailCaptchaType.UPDATE_EMAIL:
                Assert.isNull(account, AuthorizeBizCode.EMAIL_EXIST);
                break;
            default:
                throw new BizException(AuthorizeBizCode.CAPTCHA_TYPE_NOT_SUPPORTED);
        }
        String captcha = RandomUtils.randomInt(4);
        captchaService.email(body.getType(), body.getEmail(), captcha);
        return Response.success();
    }
}
