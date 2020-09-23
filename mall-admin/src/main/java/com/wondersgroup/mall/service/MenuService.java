package com.wondersgroup.mall.service;

import com.wondersgroup.mall.dto.UmsMenuNode;
import com.wondersgroup.mall.model.UmsMenu;

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

    /**
     * 分页查询后台菜单
     * @param parentId
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<UmsMenu> list(Long parentId,Integer pageSize,Integer pageNum);

    /**
     * 修改菜单的显示状态
     * @param id
     * @param hidden
     * @return
     */
    int updateHidden(Long id,Integer hidden);

    /**
     * 根据菜单id获取详情
     * @param id
     * @return
     */
    UmsMenu getIten(Long id);

    /**
     * 修改后台菜单
     * @param id
     * @return
     */
    int update(Long id,UmsMenu umsMenu);

    /**
     * 添加后台菜单
     * @param umsMenu
     * @return
     */
    int create(UmsMenu umsMenu);

    /**
     * 根据菜单id删除
     * @param id
     * @return
     */
    int delete(Long id);
}
