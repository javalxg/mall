package com.wondersgroup.mall.dao;

import com.wondersgroup.mall.model.UmsAdminRoleRelation;
import com.wondersgroup.mall.model.UmsResource;
import com.wondersgroup.mall.model.UmsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lxg
 * @Description:自定义后台用户与角色dao
 * @date 2020/9/1923:13
 */
public interface UmsAdminRoleRelationDao {
    /**
     * 获取用户所有可以访问资源
     * @param adminId
     * @return
     */
    List<UmsResource> getResourceList(@Param("adminId") Long adminId);

    /**
     * 获取所有用户
     * @param adminId
     * @return
     */
    List<UmsRole> getRoleList(@Param("adminId") Long adminId);

    /**
     * 批量插入用户角色的关系
     * @param adminRoleRelationList
     * @return
     */
    int insertList(@Param("list")List<UmsAdminRoleRelation> adminRoleRelationList);
}
