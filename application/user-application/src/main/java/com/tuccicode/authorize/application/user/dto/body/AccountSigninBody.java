package com.tuccicode.authorize.application.user.dto.body;

import com.tuccicode.authorize.domain.user.constant.UserVerifyConst;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author tucci.lee
 */
@Data
public class AccountSigninBody {

    /**
     * 字段名为username，但是传入的必须是手机号
     */
    @NotBlank
    @Pattern(regexp = UserVerifyConst.PHONE_REGEXP)
    private String username;

    @NotBlank
    private String password;
}
