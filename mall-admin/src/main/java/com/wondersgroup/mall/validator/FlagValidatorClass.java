package com.wondersgroup.mall.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author lxg
 * @Description: 状态标记校验器
 * @date 2020/9/2422:27
 */
public class FlagValidatorClass implements ConstraintValidator<FlagValidator,Integer> {
    private String[] values;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid=false;
        if (value==null){
            //当状态为空时使用默认值
            return true;
        }

        for(int i=0;i<values.length;i++){
            if(values[i].equals(String.valueOf(value))){
                isValid = true;
                break;
            }
        }
        return isValid;
    }

    @Override
    public void initialize(FlagValidator constraintAnnotation) {
        this.values=constraintAnnotation.value();
    }
}
