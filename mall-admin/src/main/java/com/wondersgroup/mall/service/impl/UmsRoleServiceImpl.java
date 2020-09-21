package com.wondersgroup.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.wondersgroup.mall.dao.UmsRoleDao;
import com.wondersgroup.mall.mapper.UmsRoleMapper;
import com.wondersgroup.mall.mapper.UmsRoleMenuRelationMapper;
import com.wondersgroup.mall.mapper.UmsRoleResourceRelationMapper;
import com.wondersgroup.mall.model.*;
import com.wondersgroup.mall.service.UmsAdminCacheService;
import com.wondersgroup.mall.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2011:49
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {
    @Autowired
    private UmsRoleDao umsRoleDao;
    @Autowired
    private UmsRoleMapper umsRoleMapper;
    @Autowired
    private UmsRoleMenuRelationMapper umsRoleMenuRelationMapper;
    @Autowired
    private UmsRoleResourceRelationMapper umsRoleResourceRelationMapper;
    @Autowired
    private UmsAdminCacheService umsAdminCacheService;
    @Override
    public int create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        return umsRoleMapper.insert(role);
    }

    @Override
    public int update(Long id, UmsRole role) {
        role.setId(id);
        return umsRoleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public int delete(List<Long> ids) {
        umsAdminCacheService.delResourceListByRoleIds(ids);
        UmsRoleExample example=new UmsRoleExample();
        example.createCriteria().andIdIn(ids);
        int count=umsRoleMapper.deleteByExample(example);
        return count;
    }

    @Override
    public List<UmsRole> list() {
        return null;
    }

    @Override
    public List<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsRoleExample example=new UmsRoleExample();
        if (!StrUtil.isEmpty(keyword)){
            example.createCriteria().andNameLike("%"+keyword+"%");
        }

        return umsRoleMapper.selectByExample(example);
    }

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        return umsRoleDao.getMenuList(adminId);
    }

    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        return umsRoleDao.getMenuListByRoleId(roleId);
    }

    @Override
    public List<UmsResource> listResource(Long roleId) {
        return umsRoleDao.getResourceListByRoleId(roleId);
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        UmsRoleMenuRelationExample example=new UmsRoleMenuRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        umsRoleMenuRelationMapper.deleteByExample(example);
        //批量插入新关系
        for (Long menuId:menuIds){
            UmsRoleMenuRelation umsRoleMenuRelation=new UmsRoleMenuRelation();
            umsRoleMenuRelation.setRoleId(roleId);
            umsRoleMenuRelation.setMenuId(menuId);
            umsRoleMenuRelationMapper.insert(umsRoleMenuRelation);
        }
        return menuIds.size();
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除原有的关系
        UmsRoleResourceRelationExample example=new UmsRoleResourceRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        umsRoleResourceRelationMapper.deleteByExample(example);
        //批量建立新的关系
        for (Long resourceId:resourceIds){
            UmsRoleResourceRelation umsRoleResourceRelation=new UmsRoleResourceRelation();
            umsRoleResourceRelation.setRoleId(roleId);
            umsRoleResourceRelation.setResourceId(resourceId);
            umsRoleResourceRelationMapper.insert(umsRoleResourceRelation);
        }
        return resourceIds.size();
    }
}
