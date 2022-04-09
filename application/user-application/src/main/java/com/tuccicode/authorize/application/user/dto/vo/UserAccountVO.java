package com.tuccicode.authorize.application.user.dto.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class UserAccountVO {

    private Long uid;

    private String email;

    private String phone;
}
