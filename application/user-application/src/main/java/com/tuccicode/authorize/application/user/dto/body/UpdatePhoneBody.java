package com.tuccicode.authorize.application.user.dto.body;

import com.tuccicode.authorize.domain.user.constant.UserVerifyConst;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author tucci.lee
 */
@Data
public class UpdatePhoneBody {

    @NotEmpty
    @Pattern(regexp = UserVerifyConst.PHONE_REGEXP)
    private String phone;

    @NotEmpty
    private String captcha;

    @NotEmpty
    private String token;

}
