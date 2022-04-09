package com.tuccicode.authorize.application.open.dto.body;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class EmailCaptchaBody {

    @NotBlank
    @Email
    private String email;

    @NotNull
    private Integer type;
}
