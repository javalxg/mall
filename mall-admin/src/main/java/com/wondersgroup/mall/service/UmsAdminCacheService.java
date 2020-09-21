package com.wondersgroup.mall.service;

import com.wondersgroup.mall.model.UmsAdmin;
import com.wondersgroup.mall.model.UmsResource;

import java.util.List;

/**
 * @author lxg
 * @Description: 后台用户缓存
 * @date 2020/9/1922:29
 */
public interface UmsAdminCacheService {
    /**
     * 获取缓存后台用户信息
     */
    UmsAdmin getAdmin(String username);

    /**
     * 缓存后台用户信息
     * @param admin
     */
    void setAdmin(UmsAdmin admin);
    /**
     * 删除后台用户缓存
     */
    void delAdmin(Long adminId);

    /**
     * 删除后台用户资源列表缓存
     */
    void delResourceList(Long adminId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRole(Long roleId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * 当资源信息改变时，删除资源项目后台用户缓存
     */
    void delResourceListByResource(Long resourceId);


    /**
     * 获取缓存后台用户资源列表
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 设置后台后台用户资源列表
     */
    void setResourceList(Long adminId, List<UmsResource> resourceList);
}
