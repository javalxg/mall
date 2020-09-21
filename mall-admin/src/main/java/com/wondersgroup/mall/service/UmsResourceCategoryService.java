package com.wondersgroup.mall.service;

import com.wondersgroup.mall.model.UmsResourceCategory;

import java.util.List;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2023:44
 */
public interface UmsResourceCategoryService {
    /**
     * 获取所有资源分类
     */
    List<UmsResourceCategory> listAll();

    /**
     * 创建资源分类
     */
    int create(UmsResourceCategory umsResourceCategory);

    /**
     * 修改资源分类
     */
    int update(Long id, UmsResourceCategory umsResourceCategory);

    /**
     * 删除资源分类
     */
    int delete(Long id);
}
