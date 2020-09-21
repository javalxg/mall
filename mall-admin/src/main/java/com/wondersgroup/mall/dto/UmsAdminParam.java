package com.wondersgroup.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author lxg
 * @Description: 用户登录参数
 * @date 2020/9/190:18
 */
@Getter
@Setter
public class UmsAdminParam {
    @NotEmpty
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @NotEmpty
    @Length(min = 8,max = 16,message = "密码长度应为8到16位")
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @Email
    @ApiModelProperty(value = "用户邮箱")
    private String email;
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    @ApiModelProperty(value = "用户手机号")
    @Pattern(regexp = "^1[3|4|5|7|8][0-9]{9}$",message = "手机号格式不正确")
    private String phone;
    @ApiModelProperty(value = "备注")
    private String note;
}
