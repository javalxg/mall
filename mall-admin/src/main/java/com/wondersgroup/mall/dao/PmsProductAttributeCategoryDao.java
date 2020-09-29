package com.wondersgroup.mall.dao;

import com.wondersgroup.mall.dto.PmsProductAttributeCategoryItem;

import java.util.List;

/**
 * @author lxg
 * @Description:自定义商品属性分类dao
 * @date 2020/9/270:07
 */
public interface PmsProductAttributeCategoryDao {
    /**
     * 获取包含属性的商品属性分类
     */
    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
