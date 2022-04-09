package com.tuccicode.authorize.application.open.controller;

import com.tuccicode.authorize.application.open.dto.body.EmailCaptchaBody;
import com.tuccicode.authorize.application.open.dto.body.SmsCaptchaBody;
import com.tuccicode.authorize.application.open.service.CaptchaAppService;
import com.tuccicode.authorize.core.annotation.Limit;
import com.tuccicode.raccoon.dto.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("captcha")
public class CaptchaController {

    private final CaptchaAppService captchaAppService;

    public CaptchaController(CaptchaAppService captchaAppService) {
        this.captchaAppService = captchaAppService;
    }

    @Limit(rate = 10, rateInterval = 24 * 60 * 60)
    @PostMapping("sms")
    public Response phone(@Valid @RequestBody SmsCaptchaBody body) {
        return captchaAppService.sendSmsCaptcha(body);
    }

    @Limit(rate = 10, rateInterval = 24 * 60 * 60)
    @PostMapping("email")
    public Response email(@Valid @RequestBody EmailCaptchaBody body) {
        return captchaAppService.sendEmailCaptcha(body);
    }
}
