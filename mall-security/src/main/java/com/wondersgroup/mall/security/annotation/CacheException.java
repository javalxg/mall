package com.wondersgroup.mall.security.annotation;

import java.lang.annotation.*;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2021:48
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
