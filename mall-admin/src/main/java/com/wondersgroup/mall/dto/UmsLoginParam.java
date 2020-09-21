package com.wondersgroup.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * @author lxg
 * @Description: 用户登录参数‘
 * @date 2020/9/1918:06
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class UmsLoginParam {
    @NotEmpty
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
