package com.wondersgroup.mall.common.exception;

import com.wondersgroup.mall.common.api.IErrorCode;

/**
 * @author lxg
 * @Description: 断言处理类用于抛出各种异常
 * @date 2020/9/1823:42
 */
public class Asserts {
    public static void fail(String message){
        throw new ApiException(message);
    }
    public static void fail(IErrorCode code){
        throw new ApiException(code);
    }
}
