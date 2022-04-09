package com.tuccicode.authorize.core.authc;

import com.tuccicode.authorize.core.token.codec.TokenCodec;
import com.tuccicode.authorize.core.token.codec.UUIDTokenCodec;
import com.tuccicode.raccoon.cache.CacheOperate;

import java.util.concurrent.TimeUnit;

/**
 * @author tucci.lee
 */
public abstract class AbstractAuthenticator implements Authenticator {

    private static final String DEFAULT_TOKEN_PREFIX = "token:";
    private static final int DEFAULT_TOKEN_EXPIRED = 30 * 60;

    private TokenCodec tokenCodec;
    private CacheOperate cacheOperate;
    private String tokenPrefix = DEFAULT_TOKEN_PREFIX;
    private int tokenExpired = DEFAULT_TOKEN_EXPIRED;

    public AbstractAuthenticator(CacheOperate cacheOperate) {
        this.tokenCodec = new UUIDTokenCodec();
        this.cacheOperate = cacheOperate;
    }

    @Override
    public String signin(Long uid) {
        String token = this.createToken(uid);
        this.saveToken(uid, token);
        this.postSignin(uid, token);
        return token;
    }

    protected abstract void postSignin(Long uid, String token);

    @Override
    public boolean isAuthenticated() {
        return getUid() != null;
    }

    @Override
    public Long getUid() {
        String token = this.getToken();
        if (token == null) {
            return null;
        }
        return parseToken(token);
    }

    @Override
    public void signout() {
        String token = this.getToken();
        this.removeToken(token);
    }

    @Override
    public String refresh() {
        String token = this.getToken();
        long expire = cacheOperate.getExpire(tokenPrefix + token);
        if(expire > 0 && expire < 432000) {
            Long uid = this.getUid();
            this.signout();
            return this.signin(uid);
        }
        return token;
    }

    /**
     * 获取token
     *
     * @return token
     */
    protected abstract String getToken();

    public String createToken(Long uid) {
        return tokenCodec.encode(uid);
    }

    public void saveToken(Long uid, String token) {
        cacheOperate.set(tokenPrefix + token, uid, tokenExpired, TimeUnit.SECONDS);
    }

    public Long parseToken(String token) {
        return cacheOperate.get(tokenPrefix + token);
    }

    public void removeToken(String token) {
        cacheOperate.delete(tokenPrefix + token);
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public int getTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(int tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    public TokenCodec getTokenCodec() {
        return tokenCodec;
    }

    public void setTokenCodec(TokenCodec tokenCodec) {
        this.tokenCodec = tokenCodec;
    }

    public CacheOperate getCacheOperate() {
        return cacheOperate;
    }

    public void setCacheOperate(CacheOperate cacheOperate) {
        this.cacheOperate = cacheOperate;
    }
}
