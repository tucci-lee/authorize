package com.tuccicode.authorize.core.aspect;

import com.tuccicode.authorize.common.exception.AuthorizeBizCode;
import com.tuccicode.authorize.core.annotation.Limit;
import com.tuccicode.authorize.core.util.WebUtils;
import com.tuccicode.raccoon.exception.BizException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 使用redisson的RRateLimiter实现
 *
 * @author tucci.lee
 */
@Aspect
@Component
public class LimitAspect {

    private static final String LIMIT_KEY = "limit:%s:%s";

    private final RedissonClient redissonClient;

    public LimitAspect(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Pointcut("@annotation(limit)")
    public void pointcut(Limit limit) {
    }

    @Before(value = "pointcut(limit)", argNames = "pjp,limit")
    public void before(JoinPoint pjp, Limit limit) {
        //获取请求的方法和key
        Method method = getMethod(pjp);
        String methodName = method.toString();
        String key = String.format(LIMIT_KEY, WebUtils.getIp(), methodName);

        long rate = limit.rate();
        int rateInterval = limit.rateInterval();

        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        rateLimiter.trySetRate(RateType.OVERALL, rate, rateInterval, RateIntervalUnit.SECONDS);
        if (!rateLimiter.tryAcquire(1)) { // 禁止访问
            throw new BizException(AuthorizeBizCode.FREQUENT_REQUESTS);
        }
        rateLimiter.expire(rateInterval, TimeUnit.SECONDS);
    }

    /**
     * 获取当前执行的方法
     *
     * @param pjp
     * @return
     */
    public Method getMethod(JoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        return methodSignature.getMethod();
    }
}
