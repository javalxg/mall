package com.wondersgroup.mall.common.api;

/**
 * @author lxg
 * @Description: 枚举常用的操作码
 * @date 2020/9/1823:31
 */
public enum  ResultCode implements  IErrorCode {
    SUCCESS(200,"操作成功"),
    FAIL(500,"操作失败"),
    VALIDATE_FAIL(404,"参数校验失败"),
    UNAUTHORIZED(401,"未登录或token过期"),
    FORBIDDEN(403,"没有相关权限"),
    USERNAME_DUPLICATE(1000,"该用户名已被注册");
    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
