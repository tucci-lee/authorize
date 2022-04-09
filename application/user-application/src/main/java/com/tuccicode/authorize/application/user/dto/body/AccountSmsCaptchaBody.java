package com.tuccicode.authorize.application.user.dto.body;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class AccountSmsCaptchaBody {

    @NotNull
    private Integer type;
}
