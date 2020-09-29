package com.wondersgroup.mall.dto;

import com.wondersgroup.mall.model.PmsProductAttribute;
import com.wondersgroup.mall.model.PmsProductAttributeCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author lxg 包含分类下属性的dto
 * @Description:
 * @date 2020/9/2423:20
 */
public class PmsProductAttributeCategoryItem extends PmsProductAttributeCategory {
    @Getter
    @Setter
    @ApiModelProperty(value = "商品属性列表")
    private List<PmsProductAttribute> productAttributeList;
}
