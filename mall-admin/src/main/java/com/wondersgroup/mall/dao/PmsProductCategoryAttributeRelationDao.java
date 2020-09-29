package com.wondersgroup.mall.dao;

import com.wondersgroup.mall.model.PmsProductCategoryAttributeRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lxg
 * @Description: 自定义商品属性分类关系dao
 * @date 2020/9/2723:17
 */
public interface PmsProductCategoryAttributeRelationDao {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<PmsProductCategoryAttributeRelation> productCategoryAttributeRelationList);
}
