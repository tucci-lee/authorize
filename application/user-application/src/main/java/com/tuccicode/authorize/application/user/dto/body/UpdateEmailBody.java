package com.tuccicode.authorize.application.user.dto.body;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * @author tucci.lee
 */
@Data
public class UpdateEmailBody {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String captcha;

    @NotEmpty
    private String token;

}
