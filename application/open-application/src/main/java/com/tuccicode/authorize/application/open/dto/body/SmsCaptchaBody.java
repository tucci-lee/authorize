package com.tuccicode.authorize.application.open.dto.body;

import com.tuccicode.authorize.domain.user.constant.UserVerifyConst;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author tucci.lee
 */
@Data
public class SmsCaptchaBody {

    @NotBlank
    @Pattern(regexp = UserVerifyConst.PHONE_REGEXP)
    private String phone;

    @NotNull
    private Integer type;
}
