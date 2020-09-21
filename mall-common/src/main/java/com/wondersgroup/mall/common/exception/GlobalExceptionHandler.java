package com.wondersgroup.mall.common.exception;

import com.wondersgroup.mall.common.api.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lxg
 * @Description: 全局异常处理器
 * @date 2020/9/1823:43
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e){
        if (e.getErrorCode()!=null){
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult handle(MethodArgumentNotValidException e){
        BindingResult bindResult=e.getBindingResult();
        String message=null;
        if (bindResult.hasErrors()){
            FieldError fieldError=bindResult.getFieldError();
                if (fieldError!=null){
                    message=fieldError.getField()+fieldError.getDefaultMessage();
                }
        }
        return CommonResult.failed(message);
    }
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public CommonResult handle(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }
}
