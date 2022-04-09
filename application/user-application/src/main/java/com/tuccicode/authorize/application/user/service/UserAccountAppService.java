package com.tuccicode.authorize.application.user.service;

import com.tuccicode.authorize.application.user.assembler.UserAccountAssembler;
import com.tuccicode.authorize.application.user.dto.body.AccountSmsCaptchaBody;
import com.tuccicode.authorize.application.user.dto.body.PhoneVerifyBody;
import com.tuccicode.authorize.application.user.dto.body.UpdateEmailBody;
import com.tuccicode.authorize.application.user.dto.body.UpdatePasswordBody;
import com.tuccicode.authorize.application.user.dto.body.UpdatePhoneBody;
import com.tuccicode.authorize.application.user.dto.vo.UserAccountVO;
import com.tuccicode.authorize.common.exception.AuthorizeBizCode;
import com.tuccicode.authorize.core.authc.Authenticator;
import com.tuccicode.authorize.domain.open.entity.captcha.EmailCaptchaType;
import com.tuccicode.authorize.domain.open.entity.captcha.SmsCaptchaType;
import com.tuccicode.authorize.domain.open.service.CaptchaService;
import com.tuccicode.authorize.domain.open.service.TokenService;
import com.tuccicode.authorize.domain.open.util.RandomUtils;
import com.tuccicode.authorize.domain.user.entity.user.UserAccount;
import com.tuccicode.authorize.domain.user.entity.user.UserAccountEmailUpdate;
import com.tuccicode.authorize.domain.user.entity.user.UserAccountPasswordUpdate;
import com.tuccicode.authorize.domain.user.entity.user.UserAccountPhoneUpdate;
import com.tuccicode.authorize.domain.user.service.UserAccountService;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.raccoon.dto.SingletonResponse;
import com.tuccicode.raccoon.exception.BizException;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
public class UserAccountAppService {

    private final Authenticator authenticator;
    private final UserAccountService userAccountService;
    private final CaptchaService captchaService;
    private final TokenService tokenService;

    public UserAccountAppService(Authenticator authenticator,
                                 UserAccountService userAccountService,
                                 CaptchaService captchaService,
                                 TokenService tokenService) {
        this.authenticator = authenticator;
        this.userAccountService = userAccountService;
        this.captchaService = captchaService;
        this.tokenService = tokenService;
    }

    public Response get() {
        Long uid = authenticator.getUid();
        UserAccount userAccount = userAccountService.getByUid(uid);
        userAccountService.desensitization(userAccount);

        UserAccountVO userAccountVO = UserAccountAssembler.toVO(userAccount);
        return SingletonResponse.success(userAccountVO);
    }

    public Response updatePassword(UpdatePasswordBody body) {
        Long uid = authenticator.getUid();
        UserAccount userAccount = userAccountService.getByUid(uid);
        captchaService.smsVerify(SmsCaptchaType.UPDATE_PASSWORD, userAccount.getPhone(), body.getCaptcha());
        UserAccountPasswordUpdate update = new UserAccountPasswordUpdate(uid, body.getPassword());
        userAccountService.updatePasswordByUid(update);
        return Response.success();
    }

    /**
     * 根据当前登录用户的手机号发送短信
     * 支持类型：修改密码，修改邮箱校验，修改手机校验
     *
     * @param body 请求体
     * @return Response
     */
    public Response sendSmsCaptcha(AccountSmsCaptchaBody body) {
        Long uid = authenticator.getUid();
        UserAccount userAccount = userAccountService.getByUid(uid);
        switch (body.getType()) {
            case SmsCaptchaType.UPDATE_PHONE_VERIFY:
            case SmsCaptchaType.UPDATE_EMAIL_VERIFY:
            case SmsCaptchaType.UPDATE_PASSWORD:
                break;
            default:
                throw new BizException(AuthorizeBizCode.CAPTCHA_TYPE_NOT_SUPPORTED);
        }
        String captcha = RandomUtils.randomInt(4);
        captchaService.sms(body.getType(), userAccount.getPhone(), captcha);
        return Response.success();
    }

    /**
     * 手机验证，获取允许操作的token
     *
     * @param body 请求体
     * @return token
     */
    public Response phoneVerify(PhoneVerifyBody body) {
        Long uid = authenticator.getUid();
        UserAccount userAccount = userAccountService.getByUid(uid);
        captchaService.smsVerify(body.getType(), userAccount.getPhone(), body.getCaptcha());

        String token = tokenService.getPhoneVerifyToken(body.getType(), uid);
        return SingletonResponse.success(token);
    }

    /**
     * 1. 验证短信验证码
     * 2. 验证原手机验证的token
     * 3. 更新手机号
     *
     * @param body 请求体
     * @return Response
     */
    public Response updatePhone(UpdatePhoneBody body) {
        Long uid = authenticator.getUid();
        captchaService.smsVerify(SmsCaptchaType.UPDATE_PHONE, body.getPhone(), body.getCaptcha());
        tokenService.verifyPhoneVerifyToken(SmsCaptchaType.UPDATE_PHONE_VERIFY, uid, body.getToken());

        UserAccountPhoneUpdate update = new UserAccountPhoneUpdate(uid, body.getPhone());
        userAccountService.updatePhoneByUid(update);
        return Response.success();
    }

    public Response updateEmail(UpdateEmailBody body) {
        Long uid = authenticator.getUid();
        captchaService.emailVerify(EmailCaptchaType.UPDATE_EMAIL, body.getEmail(), body.getCaptcha());
        tokenService.verifyPhoneVerifyToken(SmsCaptchaType.UPDATE_EMAIL_VERIFY, uid, body.getToken());

        UserAccountEmailUpdate update = new UserAccountEmailUpdate(uid, body.getEmail());
        userAccountService.updateEmailByUid(update);
        return Response.success();
    }
}
