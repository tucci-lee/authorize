package com.tuccicode.authorize.application.user.controller.user;

import com.tuccicode.authorize.application.user.dto.body.UpdateAvatarBody;
import com.tuccicode.authorize.application.user.dto.body.UpdateProfileBody;
import com.tuccicode.authorize.application.user.service.UserProfileAppService;
import com.tuccicode.raccoon.dto.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("/user/profile")
public class UserProfileController {

    private final UserProfileAppService userProfileAppService;

    public UserProfileController(UserProfileAppService userProfileAppService) {
        this.userProfileAppService = userProfileAppService;
    }

    /**
     * 获取用户基础信息
     *
     * @return Response
     */
    @GetMapping
    public Response getUserProfile() {
        return userProfileAppService.get();
    }

    /**
     * 更新用户基础信息
     *
     * @param body 请求体
     * @return Response
     */
    @PutMapping("/update")
    public Response updateUserProfile(@Valid @RequestBody UpdateProfileBody body) {
        return userProfileAppService.update(body);
    }

    /**
     * 修改用户头像
     *
     * @param body 请求体
     * @return Response
     */
    @PutMapping("/update_avatar")
    public Response updateUserAvatar(@Valid UpdateAvatarBody body) {
        return userProfileAppService.updateAvatar(body);
    }
}
