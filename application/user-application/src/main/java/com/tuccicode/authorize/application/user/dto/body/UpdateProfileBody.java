package com.tuccicode.authorize.application.user.dto.body;

import com.tuccicode.authorize.domain.user.entity.user.UserProfile;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author tucci.lee
 */
@Data
public class UpdateProfileBody {

    @NotBlank
    @Size(max = 20)
    private String nickname;

    private Date birthday;

    @NotBlank
    @Size(max = 20)
    private String province;

    @NotBlank
    @Size(max = 20)
    public String city;

    @NotNull
    @Max(value = UserProfile.Gender.MALE)
    private Integer gender;

    @Size(max = 200)
    private String introduction;
}
