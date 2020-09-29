package com.wondersgroup.mall.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author lxg
 * @Description: 用户验证状态是否在指定范围内的注解
 * @date 2020/9/2422:24
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = FlagValidatorClass.class)
public @interface FlagValidator {
    String[] value() default {};
    String message() default "flag is not found";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};

}
