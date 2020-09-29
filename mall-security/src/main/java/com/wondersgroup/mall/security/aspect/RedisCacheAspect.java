package com.wondersgroup.mall.security.aspect;

import com.wondersgroup.mall.security.annotation.CacheException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author lxg
 * @Description: redis缓存切面防止redis宕机影响正常业务逻辑
 * @date 2020/9/2021:43
 */
@Aspect
@Component
@Order(2)
public class RedisCacheAspect {
    private static Logger LOGGER = LoggerFactory.getLogger(RedisCacheAspect.class);
    @Pointcut("execution(public * com.wondersgroup.mall.service.*CacheService.*(..))")
    public void cacheAspect(){
    }

    @Around("cacheAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature=joinPoint.getSignature();
        MethodSignature methodSignature=(MethodSignature)signature;
        Method method=methodSignature.getMethod();
        Object result=null;
        try {
            result=joinPoint.proceed();
        }catch (Throwable throwable){
            if (method.isAnnotationPresent(CacheException.class)){
                    throw  throwable;
            }else {
                LOGGER.error("缓存失效"+throwable.getMessage());
            }
        }
        return result;
    }
}
