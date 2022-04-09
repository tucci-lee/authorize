package com.tuccicode.authorize.application.user.service;

import com.tuccicode.authorize.application.user.dto.body.AccountSigninBody;
import com.tuccicode.authorize.application.user.dto.body.SmsSigninBody;
import com.tuccicode.authorize.common.exception.AuthorizeBizCode;
import com.tuccicode.authorize.core.authc.Authenticator;
import com.tuccicode.authorize.core.util.WebUtils;
import com.tuccicode.authorize.domain.open.entity.captcha.SmsCaptchaType;
import com.tuccicode.authorize.domain.open.service.CaptchaService;
import com.tuccicode.authorize.domain.user.entity.user.SigninLogCreate;
import com.tuccicode.authorize.domain.user.entity.user.UserAccount;
import com.tuccicode.authorize.domain.user.entity.user.UserCreate;
import com.tuccicode.authorize.domain.user.service.SigninLogService;
import com.tuccicode.authorize.domain.user.service.UserAccountService;
import com.tuccicode.authorize.domain.user.service.UserService;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.raccoon.dto.SingletonResponse;
import com.tuccicode.raccoon.exception.Assert;
import com.tuccicode.raccoon.exception.BizException;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tucci.lee
 */
@Service
public class AuthorizeAppService {

    private final Authenticator authenticator;

    private final UserService userService;
    private final UserAccountService userAccountService;
    private final CaptchaService captchaService;
    private final SigninLogService signinLogService;

    public AuthorizeAppService(Authenticator authenticator,
                               UserService userService,
                               UserAccountService userAccountService,
                               CaptchaService captchaService,
                               SigninLogService signinLogService) {
        this.authenticator = authenticator;

        this.userService = userService;
        this.userAccountService = userAccountService;
        this.captchaService = captchaService;
        this.signinLogService = signinLogService;
    }

    /**
     * 短信验证码登录
     * 1. 校验验证码是否正确
     * 2. 如果账号未注册，注册一个新账号
     * 3. 根据账号uid创建token
     *
     * @param body 请求体
     * @return Response
     */
    public Response signin(SmsSigninBody body) {
        boolean status = true;
        String message = "";
        try {
            captchaService.smsVerify(SmsCaptchaType.SIGNIN, body.getPhone(), body.getCaptcha());

            UserAccount userAccount = userAccountService.getByPhone(body.getPhone());
            Long uid;
            if (userAccount == null) {
                // 注册
                UserCreate userCreate = new UserCreate(body.getPhone());
                uid = userService.create(userCreate);
            } else {
                // 判断账号是否锁定
                Assert.isTrue(!userAccount.getIsLock(), AuthorizeBizCode.ACCOUNT_LOCKED);
                uid = userAccount.getUid();
            }

            String token = authenticator.signin(uid);
            return SingletonResponse.success(token);
        } catch (BizException e) {
            status = false;
            message = e.getMessage();
            throw e;
        } catch (Exception e) {
            status = false;
            message = AuthorizeBizCode.SERVER_ERROR.getMessage();
            throw e;
        } finally {
            this.createSigninLog(body.getPhone(), status, message);
        }
    }

    /**
     * 1. 验证用户是否已经注册
     * 2. 验证密码是否正确
     * 3. 根据账号uid创建token
     *
     * @param body 请求体
     * @return Response
     */
    public Response signin(AccountSigninBody body) {
        boolean status = true;
        String message = "";
        try {
            UserAccount userAccount = userAccountService.getByPhone(body.getUsername());
            Assert.notNull(userAccount, AuthorizeBizCode.USERNAME_OR_PASSWORD_ERROR);
            boolean verify = userAccountService.passwordVerify(body.getPassword(), userAccount.getPassword());
            Assert.isTrue(verify, AuthorizeBizCode.USERNAME_OR_PASSWORD_ERROR);
            Assert.isTrue(!userAccount.getIsLock(), AuthorizeBizCode.ACCOUNT_LOCKED);

            String token = authenticator.signin(userAccount.getUid());
            return SingletonResponse.success(token);
        } catch (BizException e) {
            status = false;
            message = e.getMessage();
            throw e;
        } catch (Exception e) {
            status = false;
            message = AuthorizeBizCode.SERVER_ERROR.getMessage();
            throw e;
        } finally {
            this.createSigninLog(body.getUsername(), status, message);
        }
    }

    public Response signout() {
        authenticator.signout();
        return Response.success();
    }

    private void createSigninLog(String username, boolean status, String message) {
        HttpServletRequest request = WebUtils.getRequest();
        String ip = WebUtils.getIp();
        // 获取浏览器信息（操作系统，浏览器）
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader(HttpHeaders.USER_AGENT));
        String os = userAgent.getOperatingSystem().getName();
        String browser = userAgent.getBrowser().getName();
        SigninLogCreate log = new SigninLogCreate(username, ip, os, browser, status, message);
        signinLogService.create(log);
    }
}
