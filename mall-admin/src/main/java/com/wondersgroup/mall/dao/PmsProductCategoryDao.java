package com.wondersgroup.mall.dao;

import com.wondersgroup.mall.dto.PmsProductCategoryWithChildrenItem;

import java.util.List;

/**
 * @author lxg
 * @Description: 商品分类自定义dao
 * @date 2020/9/2422:43
 */
public interface PmsProductCategoryDao {
    /**
     * 获取商品分类及其子分类
     */
    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
