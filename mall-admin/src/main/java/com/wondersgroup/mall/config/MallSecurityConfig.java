package com.wondersgroup.mall.config;

import com.wondersgroup.mall.model.UmsResource;
import com.wondersgroup.mall.security.component.DynamicSecurityService;
import com.wondersgroup.mall.service.UmsAdminService;
import com.wondersgroup.mall.security.config.SecurityConfig;
import com.wondersgroup.mall.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lxg
 * @Description: mall-security相关的配置
 * @date 2020/9/1914:59
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MallSecurityConfig extends SecurityConfig {
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsResourceService umsResourceService;
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> adminService.loadUserByUsername(username);
    }
    @Bean
    public DynamicSecurityService dynamicSecurityService(){
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map=new ConcurrentHashMap<>();
                List<UmsResource> resourceList=umsResourceService.listAll();
                for (UmsResource url:resourceList){
                    map.put(url.getUrl(), new org.springframework.security.access.SecurityConfig(url.getId()+":"+url.getName()));
                }
                return map;
            }
        };
    }
}
