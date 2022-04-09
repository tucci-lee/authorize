package com.tuccicode.authorize.application.user.controller.user;

import com.tuccicode.authorize.application.user.dto.body.AccountSmsCaptchaBody;
import com.tuccicode.authorize.application.user.dto.body.PhoneVerifyBody;
import com.tuccicode.authorize.application.user.dto.body.UpdateEmailBody;
import com.tuccicode.authorize.application.user.dto.body.UpdatePasswordBody;
import com.tuccicode.authorize.application.user.dto.body.UpdatePhoneBody;
import com.tuccicode.authorize.application.user.service.UserAccountAppService;
import com.tuccicode.authorize.core.annotation.Limit;
import com.tuccicode.raccoon.dto.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("/user/account")
public class UserAccountController {

    private final UserAccountAppService userAccountAppService;

    public UserAccountController(UserAccountAppService userAccountAppService) {
        this.userAccountAppService = userAccountAppService;
    }

    /**
     * 获取账号信息
     *
     * @return Response
     */
    @GetMapping
    public Response getUserAccount() {
        return userAccountAppService.get();
    }

    /**
     * 发送修改账号信息的验证码
     *
     * @param body 请求体
     * @return Response
     */
    @Limit(rate = 10, rateInterval = 24 * 60 * 60)
    @PostMapping("/captcha/sms")
    public Response sendSmsCaptcha(@Valid @RequestBody AccountSmsCaptchaBody body) {
        return userAccountAppService.sendSmsCaptcha(body);
    }

    /**
     * 修改密码
     *
     * @param body 请求体
     * @return Response
     */
    @PutMapping("update_password")
    public Response updatePassword(@Valid @RequestBody UpdatePasswordBody body) {
        return userAccountAppService.updatePassword(body);
    }

    /**
     * 手机验证，修改敏感信息之前需要验证手机
     *
     * @param body 请求体
     * @return 验证token
     */
    @PostMapping("phone_verify")
    public Response phoneVerify(@Valid @RequestBody PhoneVerifyBody body) {
        return userAccountAppService.phoneVerify(body);
    }

    /**
     * 修改手机号
     *
     * @param body 请求体
     * @return Response
     */
    @PutMapping("update_phone")
    public Response updatePhone(@Valid @RequestBody UpdatePhoneBody body) {
        return userAccountAppService.updatePhone(body);
    }

    /**
     * 修改邮箱
     *
     * @param body 请求体
     * @return Response
     */
    @PutMapping("update_email")
    public Response updateEmail(@Valid @RequestBody UpdateEmailBody body) {
        return userAccountAppService.updateEmail(body);
    }
}
