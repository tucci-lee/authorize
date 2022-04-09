package com.tuccicode.authorize.application.user.dto.body;

import com.tuccicode.authorize.domain.user.constant.UserVerifyConst;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author tucci.lee
 */
@Data
public class UpdatePasswordBody {

    @NotBlank
    @Pattern(regexp = UserVerifyConst.PASSWORD_REGEXP)
    private String password;

    @NotBlank
    private String captcha;
}
