package com.tuccicode.authorize.config;

import com.tuccicode.authorize.core.authc.Authenticator;
import com.tuccicode.authorize.core.authc.DefaultAuthenticator;
import com.tuccicode.authorize.core.filter.UserFilter;
import com.tuccicode.raccoon.cache.CacheOperate;
import com.tuccicode.raccoon.cache.RedisTemplateCacheOperate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tucci.lee
 */
@Configuration
public class SsoAutoConfiguration {

    @Bean
    public CacheOperate cacheOperate(RedisTemplate redisTemplate) {
        return new RedisTemplateCacheOperate(redisTemplate);
    }

    @Bean
    public Authenticator authenticator(CacheOperate cacheOperate) {
        DefaultAuthenticator authenticator = new DefaultAuthenticator(cacheOperate);
        authenticator.setTokenExpired(2592000);
        authenticator.setDomain("2cci.cn");
        return authenticator;
    }

    @Bean
    public UserFilter userFilter(Authenticator authenticator) {
        List<String> excludeUrls = new ArrayList<>();
        excludeUrls.add("/error/**");
        excludeUrls.add("/captcha/**");
        excludeUrls.add("/signin/**");
        excludeUrls.add("/open/**");
        UserFilter userFilter = new UserFilter(authenticator);
        userFilter.setExcludeUrls(excludeUrls);
        return userFilter;
    }
}
