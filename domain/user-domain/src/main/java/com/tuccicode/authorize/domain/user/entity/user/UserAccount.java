package com.tuccicode.authorize.domain.user.entity.user;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class UserAccount extends DTO {

    private Long uid;

    private String email;

    private String phone;

    private String username;

    private String password;

    private Boolean isLock;

    private Long createTime;

    private Long updatedTime;
}
