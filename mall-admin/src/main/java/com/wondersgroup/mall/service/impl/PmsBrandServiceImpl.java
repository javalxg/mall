package com.wondersgroup.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.wondersgroup.mall.dto.PmsBrandParam;
import com.wondersgroup.mall.mapper.PmsBrandMapper;
import com.wondersgroup.mall.model.PmsBrand;
import com.wondersgroup.mall.model.PmsBrandExample;
import com.wondersgroup.mall.service.PmsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2422:30
 */
@Service
public class PmsBrandServiceImpl implements PmsBrandService {
    @Autowired
    private PmsBrandMapper brandMapper;
    @Override
    public List<PmsBrand> listAllBrand() {

        return null;
    }

    @Override
    public int createBrand(PmsBrandParam pmsBrandParam) {
        return 0;
    }

    @Override
    public int updateBrand(Long id, PmsBrandParam pmsBrandParam) {
        return 0;
    }

    @Override
    public int deleteBrand(Long id) {
        return 0;
    }

    @Override
    public int deleteBrand(List<Long> ids) {
        return 0;
    }

    @Override
    public List<PmsBrand> listBrand(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PmsBrandExample example=new PmsBrandExample();
        example.setOrderByClause("sort desc");
        PmsBrandExample.Criteria criteria=example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        return brandMapper.selectByExample(example);

    }

    @Override
    public PmsBrand getBrand(Long id) {
        return null;
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        return 0;
    }

    @Override
    public int updateFactoryStatus(List<Long> ids, Integer factoryStatus) {
        return 0;
    }
}
