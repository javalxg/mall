package com.wondersgroup.mall.service;

import com.wondersgroup.mall.dto.UmsMenuNode;

import java.util.List;

/**
 * @author lxg
 * @Description: 后台菜单
 * @date 2020/9/2015:32
 */
public interface MenuService {
    /**
     *树形结构返回所有菜单的列表
     * @return
     */
    List<UmsMenuNode> treeList();
}
