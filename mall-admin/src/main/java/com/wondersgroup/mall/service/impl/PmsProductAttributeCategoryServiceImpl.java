package com.wondersgroup.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.wondersgroup.mall.dao.PmsProductAttributeCategoryDao;
import com.wondersgroup.mall.dto.PmsProductAttributeCategoryItem;
import com.wondersgroup.mall.mapper.PmsProductAttributeCategoryMapper;
import com.wondersgroup.mall.model.PmsProductAttributeCategory;
import com.wondersgroup.mall.model.PmsProductAttributeCategoryExample;
import com.wondersgroup.mall.service.PmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2423:22
 */
@Service
public class PmsProductAttributeCategoryServiceImpl implements PmsProductAttributeCategoryService {
    @Autowired
    private PmsProductAttributeCategoryMapper pmsProductAttributeCategoryMapper;
    @Autowired
    private PmsProductAttributeCategoryDao pmsProductAttributeCategoryDao;
    @Override
    public int create(String name) {
        PmsProductAttributeCategory category=new PmsProductAttributeCategory();
        category.setName(name);
        return pmsProductAttributeCategoryMapper.insertSelective(category);
    }

    @Override
    public int update(Long id, String name) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public PmsProductAttributeCategory getItem(Long id) {
        return null;
    }

    @Override
    public List<PmsProductAttributeCategory> getList(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);

         return pmsProductAttributeCategoryMapper.selectByExample(new PmsProductAttributeCategoryExample());
    }

    @Override
    public List<PmsProductAttributeCategoryItem> getListWithAttr() {

        return pmsProductAttributeCategoryDao.getListWithAttr();
    }
}
