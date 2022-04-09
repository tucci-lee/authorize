package com.tuccicode.authorize.application.user.controller;

import com.tuccicode.authorize.application.user.dto.body.AccountSigninBody;
import com.tuccicode.authorize.application.user.dto.body.SmsSigninBody;
import com.tuccicode.authorize.application.user.service.AuthorizeAppService;
import com.tuccicode.authorize.core.annotation.Limit;
import com.tuccicode.raccoon.dto.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author tucci.lee
 */
@RestController
public class AuthorizeController {

    private final AuthorizeAppService authorizeAppService;

    public AuthorizeController(AuthorizeAppService authorizeAppService) {
        this.authorizeAppService = authorizeAppService;
    }

    /**
     * 短信验证码登录
     *
     * @param body 请求体
     * @return Response
     */
    @Limit(rate = 20, rateInterval = 24 * 60 * 60)
    @PostMapping("/signin/sms")
    public Response signin(@Valid @RequestBody SmsSigninBody body) {
        return authorizeAppService.signin(body);
    }

    /**
     * 账号密码登录
     *
     * @param body 请求体
     * @return Response
     */
    @Limit(rate = 20, rateInterval = 24 * 60 * 60)
    @PostMapping("/signin/account")
    public Response signin(@Valid @RequestBody AccountSigninBody body) {
        return authorizeAppService.signin(body);
    }

    /**
     * 登出
     *
     * @return Response
     */
    @PostMapping("/signout")
    public Response signout() {
        return authorizeAppService.signout();
    }

    /**
     * 验证是否登录，如果未登录会被filter拦截
     *
     * @return Response
     */
    @GetMapping("verify")
    public Response verify() {
        return Response.success();
    }
}
