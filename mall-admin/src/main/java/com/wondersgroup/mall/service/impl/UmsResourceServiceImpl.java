package com.wondersgroup.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.wondersgroup.mall.mapper.UmsResourceMapper;
import com.wondersgroup.mall.model.UmsResource;
import com.wondersgroup.mall.model.UmsResourceExample;
import com.wondersgroup.mall.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2023:53
 */
@Service
public class UmsResourceServiceImpl implements UmsResourceService {
    @Autowired
    private UmsResourceMapper umsResourceMapper;
    @Override
    public int create(UmsResource umsResource) {
        umsResource.setCreateTime(new Date());
        return umsResourceMapper.insert(umsResource);
    }

    @Override
    public int update(Long id, UmsResource umsResource) {
        umsResource.setId(id);
        return umsResourceMapper.updateByPrimaryKey(umsResource);
    }

    @Override
    public UmsResource getItem(Long id) {
        return umsResourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Long id) {
        return umsResourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsResourceExample example=new UmsResourceExample();
        UmsResourceExample.Criteria criteria=example.createCriteria();
        if (categoryId!=null){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if (StrUtil.isNotEmpty(nameKeyword)){
            criteria.andNameLike("%"+nameKeyword+"%");
        }
        if (StrUtil.isNotEmpty(urlKeyword)){
            criteria.andUrlLike("%"+urlKeyword+"%");
        }
        return umsResourceMapper.selectByExample(example);
    }

    @Override
    public List<UmsResource> listAll() {
        return umsResourceMapper.selectByExample(new UmsResourceExample());

    }
}
