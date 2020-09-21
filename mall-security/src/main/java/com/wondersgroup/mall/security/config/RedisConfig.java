package com.wondersgroup.mall.security.config;

import com.wondersgroup.mall.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/1922:25
 */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {
}
