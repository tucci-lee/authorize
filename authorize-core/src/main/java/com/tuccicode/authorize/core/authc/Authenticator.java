package com.tuccicode.authorize.core.authc;

/**
 * @author tucci.lee
 */
public interface Authenticator {

    /**
     * 登录请求生成token
     *
     * @param uid 用户uid
     * @return token
     */
    String signin(Long uid);

    /**
     * 是否认证
     *
     * @return 是否认证
     */
    boolean isAuthenticated();

    /**
     * 获取用户uid
     *
     * @return uid
     */
    Long getUid();

    /**
     * 登出
     */
    void signout();

    /**
     * 获取token
     *
     * @return token
     */
    String refresh();

}
