package com.tuccicode.authorize.core.authc;

import com.tuccicode.authorize.common.exception.AuthorizeBizCode;
import com.tuccicode.authorize.core.util.WebUtils;
import com.tuccicode.raccoon.cache.CacheOperate;
import com.tuccicode.raccoon.exception.BizException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tucci.lee
 */
public class DefaultAuthenticator extends AbstractAuthenticator {
    public static final String DEFAULT_COOKIE_NAME = "sso-token";
    public static final String DEFAULT_DOMAIN = "localhost";
    public static final int DEFAULT_ONLINE_COUNT = -1;
    /**
     * 允许用户同时在线数
     */
    private int onlineCount = DEFAULT_ONLINE_COUNT;
    /**
     * 如果超出同时在线数，是否踢出最早登录的用户
     */
    private boolean kickedOut = true;
    private String cookieName = DEFAULT_COOKIE_NAME;
    private String domain = DEFAULT_DOMAIN;

    public DefaultAuthenticator(CacheOperate cacheOperate) {
        super(cacheOperate);
    }

    @Override
    public void postSignin(Long uid, String token) {
        this.doOnline(uid, token);
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setPath("/");
        cookie.setDomain(domain);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(super.getTokenExpired());
        WebUtils.getResponse().addCookie(cookie);
    }

    @Override
    protected String getToken() {
        HttpServletRequest request = WebUtils.getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 同时在线用户处理
     *
     * @param uid
     * @param token
     */
    protected void doOnline(Long uid, String token) {
        CacheOperate cacheOperate = super.getCacheOperate();
        String tokenPrefix = super.getTokenPrefix();
        // -1 为不限制同时在线
        if (onlineCount == -1) {
            return;
        }
        // 获取uid已经登录的所有token
        String cacheKey = tokenPrefix + uid;
        List<String> cacheTokens = cacheOperate.get(cacheKey);
        if (cacheTokens == null) {
            cacheTokens = new ArrayList<>();
            cacheTokens.add(token);
            cacheOperate.set(cacheKey, cacheTokens);
            return;
        }
        // 获取有限的已经登录的token
        List<String> tokens = new ArrayList<>();
        for (String cacheToken : cacheTokens) {
            Long cacheUid = cacheOperate.get(tokenPrefix + cacheToken);
            if (cacheUid != null) {
                tokens.add(cacheToken);
            }
        }
        // 判断同时登录次数
        int size = tokens.size();
        if (size >= onlineCount && kickedOut) {
            // 踢出最先登录的用户
            String firstToken = tokens.get(0);
            cacheOperate.delete(tokenPrefix + firstToken);
            tokens.remove(0);
        } else if (size >= onlineCount) {
            // 不踢出任何用户，当前登录token失效
            cacheOperate.delete(tokenPrefix + token);
            throw new BizException(AuthorizeBizCode.SIGNIN_DENIED);
        }
        tokens.add(token);
        cacheOperate.set(cacheKey, tokens);
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public boolean getKickedOut() {
        return kickedOut;
    }

    public void setKickedOut(boolean kickedOut) {
        this.kickedOut = kickedOut;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }
}
