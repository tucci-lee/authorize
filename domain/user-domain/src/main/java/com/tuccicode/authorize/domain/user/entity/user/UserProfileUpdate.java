package com.tuccicode.authorize.domain.user.entity.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class UserProfileUpdate {

    private Long uid;

    private String avatar;

    private String nickname;

    private Date birthday;

    private String province;

    public String city;

    private Integer gender;

    private String introduction;

    public UserProfileUpdate(Long uid) {
        this.uid = uid;
    }
}
