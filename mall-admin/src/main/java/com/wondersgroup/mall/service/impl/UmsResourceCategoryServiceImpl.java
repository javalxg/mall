package com.wondersgroup.mall.service.impl;

import com.wondersgroup.mall.mapper.UmsResourceCategoryMapper;
import com.wondersgroup.mall.model.UmsResourceCategory;
import com.wondersgroup.mall.model.UmsResourceCategoryExample;
import com.wondersgroup.mall.service.UmsResourceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2023:45
 */
@Service
public class UmsResourceCategoryServiceImpl implements UmsResourceCategoryService {
  @Autowired
  private UmsResourceCategoryMapper umsResourceCategoryMapper;
    @Override
    public List<UmsResourceCategory> listAll() {
        UmsResourceCategoryExample example=new UmsResourceCategoryExample();
        example.setOrderByClause("sort desc");

        return umsResourceCategoryMapper.selectByExample(example);
    }

    @Override
    public int create(UmsResourceCategory umsResourceCategory) {
        return 0;
    }

    @Override
    public int update(Long id, UmsResourceCategory umsResourceCategory) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }
}
