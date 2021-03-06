package com.tuccicode.authorize.application.user.service;

import com.tuccicode.authorize.application.user.assembler.UserProfileAssembler;
import com.tuccicode.authorize.application.user.dto.body.UpdateAvatarBody;
import com.tuccicode.authorize.application.user.dto.body.UpdateProfileBody;
import com.tuccicode.authorize.application.user.dto.vo.UserProfileVO;
import com.tuccicode.authorize.core.authc.Authenticator;
import com.tuccicode.authorize.domain.open.constant.OpenFileConst;
import com.tuccicode.authorize.domain.open.external.FsService;
import com.tuccicode.authorize.domain.user.constant.UserLockConst;
import com.tuccicode.authorize.domain.user.entity.user.UserProfile;
import com.tuccicode.authorize.domain.user.entity.user.UserProfileUpdate;
import com.tuccicode.authorize.domain.user.service.UserProfileService;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.raccoon.dto.SingletonResponse;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author tucci.lee
 */
@Service
public class UserProfileAppService {

    private final Authenticator authenticator;
    private final UserProfileService userProfileService;
    private final FsService fsService;
    private final AsyncTaskExecutor asyncTaskExecutor;
    private final RedissonClient redissonClient;

    public UserProfileAppService(Authenticator authenticator,
                                 UserProfileService userProfileService,
                                 FsService fsService,
                                 AsyncTaskExecutor asyncTaskExecutor,
                                 RedissonClient redissonClient) {
        this.authenticator = authenticator;
        this.userProfileService = userProfileService;
        this.fsService = fsService;
        this.asyncTaskExecutor = asyncTaskExecutor;
        this.redissonClient = redissonClient;
    }

    /**
     * ???????????????????????????token?????????token??????5???????????????????????????token???
     * ????????????html???????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @return UserProfileVO
     */
    public Response get() {
        Long uid = authenticator.getUid();
        UserProfile userProfile = userProfileService.getByUid(uid);

        String token;
        RLock lock = redissonClient.getLock(UserLockConst.SIGNIN_TOKEN + uid);
        lock.lock();
        try {
            token = authenticator.refresh();
        } finally {
            lock.unlock();
        }
        UserProfileVO userProfileVO = UserProfileAssembler.toVO(userProfile, fsService.getDomain(), token);
        return SingletonResponse.success(userProfileVO);
    }

    public Response update(UpdateProfileBody body) {
        Long uid = authenticator.getUid();
        UserProfileUpdate profileUpdate = new UserProfileUpdate(uid);
        BeanUtils.copyProperties(body, profileUpdate);
        userProfileService.updateByUid(profileUpdate);

        return Response.success();
    }

    /**
     * 1. ???????????????????????????
     * 2. ????????????
     * 3. ????????????????????????
     * 4. ??????????????????
     * 5. ???????????????
     *
     * @param body ????????????
     * @return ????????????
     */
    public Response updateAvatar(UpdateAvatarBody body) {
        Long uid = authenticator.getUid();
        MultipartFile file = body.getFile();
        String prefix = String.format(OpenFileConst.AVATAR_PREFIX, uid);
        String avatar = OpenFileConst.formatName(prefix, file.getOriginalFilename());

        try {
            fsService.upload(avatar, file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("?????????????????????");
        }

        UserProfile userProfile = userProfileService.getByUid(uid);

        UserProfileUpdate profileUpdate = new UserProfileUpdate(uid)
                .setAvatar(avatar);
        userProfileService.updateByUid(profileUpdate);

        if (userProfile.getAvatar() != null) {
            asyncTaskExecutor.execute(() -> fsService.delete(userProfile.getAvatar()));
        }
        return SingletonResponse.success(fsService.getDomain() + avatar);
    }
}
