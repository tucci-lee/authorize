package com.tuccicode.authorize.application.user.dto.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class UserProfileVO {

    private Long uid;

    private String avatar;

    private String nickname;

    private Date birthday;

    private String province;

    private String city;

    private Integer gender;

    private String introduction;

    private Long createTime;

    private Long updatedTime;

    private String token;
}
