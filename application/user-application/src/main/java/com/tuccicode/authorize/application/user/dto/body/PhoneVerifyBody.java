package com.tuccicode.authorize.application.user.dto.body;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class PhoneVerifyBody {

    @NotNull
    private Integer type;

    @NotBlank
    private String captcha;
}
