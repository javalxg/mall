package com.wondersgroup.mall.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * @author lxg
 * @Description: 动态权限相关业务类
 * @date 2020/9/2123:48
 */
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应MAP
     */
    Map<String, ConfigAttribute> loadDataSource();
}
