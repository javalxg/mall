package com.wondersgroup.mall.config;

import com.wondersgroup.mall.common.config.BaseSwaggerConfig;
import com.wondersgroup.mall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lxg
 * @Description: swagger配置类
 * @date 2020/9/1917:50
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.wondersgroup.mall.controller")
                .title("mall后台系统")
                .description("mall后台相关接口文档")
                .contactName("wondersgroup")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }
}
