package com.wondersgroup.mall.dao;

import com.wondersgroup.mall.model.UmsMenu;
import com.wondersgroup.mall.model.UmsResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lxg
 * @Description:  自定义后台角色管理Dao
 * @date 2020/9/2011:50
 */
public interface UmsRoleDao {
    /**
     * 根据用户id获取菜单
     * @param adminId
     * @return
     */
    List<UmsMenu> getMenuList(@Param("adminId")Long adminId);

    /**
     * 根据角色id获取菜单
     * @param roleId
     * @return
     */
    List<UmsMenu> getMenuListByRoleId(@Param("roleId")Long roleId);

    /**
     * 根据角色id获取资源
     * @param roleId
     * @return
     */
    List<UmsResource> getResourceListByRoleId(@Param("roleId")Long roleId);

}
