package com.wondersgroup.mall.service;

import com.wondersgroup.mall.common.exception.ApiException;
import com.wondersgroup.mall.dto.UmsAdminParam;
import com.wondersgroup.mall.model.UmsAdmin;
import com.wondersgroup.mall.model.UmsResource;
import com.wondersgroup.mall.model.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lxg
 * @Description: 后台管理员service
 * @date 2020/9/190:31
 */
public interface UmsAdminService {
    /*根据用户名获取后台管理员*/
    UmsAdmin getAdminByUsername(String userName) throws ApiException;
    /*注册*/
    UmsAdmin register(UmsAdminParam umsAdminParam) throws ApiException;

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username) throws ApiException;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    String login(String username,String password);

    /**
     * 获取指定用户的可访问资源
     */
    public List<UmsResource> getResourceList(Long adminId);

    /**
     * 根据用户id获取角色
     * @param adminId
     * @return
     */
    public List<UmsRole> getRoleList(Long adminId);

    /**
     * 根据用户名称或者昵称分页查询用户
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    List<UmsAdmin> list(String keyword,Integer pageSize,Integer pageNum);

    /**
     * 修改账号的状态
     * @param adminId
     * @param umsAdmin
     * @return
     */
    int update(Long adminId,UmsAdmin umsAdmin);

    /**
     * 根据用户id获取用户
     * @param id
     * @return
     */
    UmsAdmin getItem(Long id);

    /**
     * 修改用户的角色
     * @param adminId
     * @param roleIds
     * @return
     */
    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);

    /**
     * 删除指定用户
     * @param adminId
     * @return
     */
    int delete(Long adminId);

}
