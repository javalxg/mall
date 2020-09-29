package com.wondersgroup.mall.service;

import com.wondersgroup.mall.dto.PmsProductAttributeCategoryItem;
import com.wondersgroup.mall.model.PmsProductAttributeCategory;

import java.util.List;

/**
 * @author lxg
 * @Description: 商品属性分类service
 * @date 2020/9/2423:19
 */
public interface PmsProductAttributeCategoryService {
    /**
     * 创建属性分类
     */
    int create(String name);

    /**
     * 修改属性分类
     */
    int update(Long id, String name);

    /**
     * 删除属性分类
     */
    int delete(Long id);

    /**
     * 获取属性分类详情
     */
    PmsProductAttributeCategory getItem(Long id);

    /**
     * 分页查询属性分类
     */
    List<PmsProductAttributeCategory> getList(Integer pageSize, Integer pageNum);

    /**
     * 获取包含属性的属性分类
     */
    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
