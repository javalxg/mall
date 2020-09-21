package com.wondersgroup.mall.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxg
 * @Description: 白名单配置读取类
 * @date 2020/9/1915:05
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlConfig {
    private List<String> urls=new ArrayList<>();
}
