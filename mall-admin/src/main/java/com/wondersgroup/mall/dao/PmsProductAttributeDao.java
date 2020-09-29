package com.wondersgroup.mall.dao;

import com.wondersgroup.mall.dto.ProductAttrInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lxg
 * @Description:自定义商品属性dao
 * @date 2020/9/2723:28
 */
public interface PmsProductAttributeDao {
    /**
     * 获取商品属性信息
     */
    List<ProductAttrInfo> getProductAttrInfo(@Param("id") Long productCategoryId);
}
