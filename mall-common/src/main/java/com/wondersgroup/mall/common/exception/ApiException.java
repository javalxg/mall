package com.wondersgroup.mall.common.exception;

import com.wondersgroup.mall.common.api.IErrorCode;

/**
 * @author lxg
 * @Description: api异常
 * @date 2020/9/1823:41
 */
public class ApiException extends RuntimeException {
    private IErrorCode errorCode;
    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
