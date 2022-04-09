package com.tuccicode.authorize.domain.user.entity.user;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class UserProfile extends DTO {

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

    public interface Gender {
        int FEMALE = 0;
        int MALE = 1;
    }
}
