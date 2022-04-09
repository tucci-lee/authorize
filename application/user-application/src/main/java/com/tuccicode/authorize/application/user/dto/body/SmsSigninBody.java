package com.tuccicode.authorize.application.user.dto.body;

import com.tuccicode.authorize.domain.user.constant.UserVerifyConst;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author tucci.lee
 */
@Data
public class SmsSigninBody {

    @NotBlank
    @Pattern(regexp = UserVerifyConst.PHONE_REGEXP)
    private String phone;

    @NotBlank
    private String captcha;
}
