package com.wondersgroup.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.wondersgroup.mall.dao.PmsProductAttributeDao;
import com.wondersgroup.mall.dto.PmsProductAttributeParam;
import com.wondersgroup.mall.dto.ProductAttrInfo;
import com.wondersgroup.mall.mapper.PmsProductAttributeCategoryMapper;
import com.wondersgroup.mall.mapper.PmsProductAttributeMapper;
import com.wondersgroup.mall.model.PmsProductAttribute;
import com.wondersgroup.mall.model.PmsProductAttributeCategory;
import com.wondersgroup.mall.model.PmsProductAttributeExample;
import com.wondersgroup.mall.service.PmsProductAttributeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2622:03
 */
@Service
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService {
    @Autowired
    private PmsProductAttributeMapper pmsProductAttributeMapper;
    @Autowired
    private PmsProductAttributeDao pmsProductAttributeDao;
    @Autowired
    private PmsProductAttributeCategoryMapper pmsProductAttributeCategoryMapper;
    @Override
    public List<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductAttributeExample example=new PmsProductAttributeExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andProductAttributeCategoryIdEqualTo(cid).andTypeEqualTo(type);
        return pmsProductAttributeMapper.selectByExample(example);
    }

    @Override
    public int create(PmsProductAttributeParam pmsProductAttributeParam) {
        PmsProductAttribute pmsProductAttribute=new PmsProductAttribute();
        BeanUtils.copyProperties(pmsProductAttributeParam, pmsProductAttribute);
        int count=  pmsProductAttributeMapper.insertSelective(pmsProductAttribute);
        //新增商品属性后需要更新商品属性分类数量
        PmsProductAttributeCategory pmsProductAttributeCategory= pmsProductAttributeCategoryMapper.selectByPrimaryKey(pmsProductAttribute.getProductAttributeCategoryId());
        if (pmsProductAttribute.getType()==0){
            pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount()+1);
        }else if(pmsProductAttribute.getType()==1){
            pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount()+1);
        }
        pmsProductAttributeCategoryMapper.updateByPrimaryKey(pmsProductAttributeCategory);
        return count;
    }

    @Override
    public int update(Long id, PmsProductAttributeParam productAttributeParam) {
        return 0;
    }

    @Override
    public PmsProductAttribute getItem(Long id) {
        return null;
    }

    @Override
    public int delete(List<Long> ids) {
        PmsProductAttributeExample example=new PmsProductAttributeExample();
       // example.createCriteria().andIdIn()
        return 0;
    }

    @Override
    public List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId) {
        return pmsProductAttributeDao.getProductAttrInfo(productCategoryId);
    }
}
