package com.tuccicode.authorize.domain.user.convertor;

import com.tuccicode.authorize.domain.user.db.UserAccountDO;
import com.tuccicode.authorize.domain.user.db.UserProfileDO;
import com.tuccicode.authorize.domain.user.entity.user.UserAccountEmailUpdate;
import com.tuccicode.authorize.domain.user.entity.user.UserAccountPasswordUpdate;
import com.tuccicode.authorize.domain.user.entity.user.UserAccount;
import com.tuccicode.authorize.domain.user.entity.user.UserAccountPhoneUpdate;
import com.tuccicode.authorize.domain.user.entity.user.UserCreate;
import com.tuccicode.authorize.domain.user.entity.user.UserProfile;
import com.tuccicode.authorize.domain.user.entity.user.UserProfileUpdate;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author tucci.lee
 */
public class UserConvertor {

    public static UserAccount toAccountEntity(UserAccountDO db) {
        if (db == null) {
            return null;
        }
        UserAccount entity = new UserAccount();
        BeanUtils.copyProperties(db, entity);
        return entity;
    }

    public static UserProfile toProfileEntity(UserProfileDO db) {
        if (db == null) {
            return null;
        }
        UserProfile entity = new UserProfile();
        BeanUtils.copyProperties(db, entity);
        return entity;
    }

    public static UserAccountDO toAccountCreateDO(UserCreate entity) {
        return new UserAccountDO()
                .setPhone(entity.getPhone())
                .setCreateTime(System.currentTimeMillis());
    }

    public static UserProfileDO toProfileCreateDO(long uid) {
        return new UserProfileDO()
                .setUid(uid)
                .setCreateTime(System.currentTimeMillis());
    }

    public static UserProfileDO toProfileUpdateDO(UserProfileUpdate entity) {
        return new UserProfileDO()
                .setUid(entity.getUid())
                .setAvatar(entity.getAvatar())
                .setNickname(entity.getNickname())
                .setBirthday(entity.getBirthday())
                .setProvince(entity.getProvince())
                .setCity(entity.getCity())
                .setGender(entity.getGender())
                .setIntroduction(entity.getIntroduction())
                .setUpdatedTime(System.currentTimeMillis());
    }

    public static UserAccountDO toAccountPasswordUpdateDO(UserAccountPasswordUpdate update) {
        return new UserAccountDO()
                .setUid(update.getUid())
                .setPassword(BCrypt.hashpw(update.getPassword(), BCrypt.gensalt()))
                .setUpdatedTime(System.currentTimeMillis());
    }

    public static UserAccountDO toAccountPhoneUpdateDO(UserAccountPhoneUpdate update) {
        return new UserAccountDO()
                .setUid(update.getUid())
                .setPhone(update.getPhone())
                .setUpdatedTime(System.currentTimeMillis());
    }

    public static UserAccountDO toAccountEmailUpdateDO(UserAccountEmailUpdate update) {
        return new UserAccountDO()
                .setUid(update.getUid())
                .setEmail(update.getEmail())
                .setUpdatedTime(System.currentTimeMillis());
    }
}