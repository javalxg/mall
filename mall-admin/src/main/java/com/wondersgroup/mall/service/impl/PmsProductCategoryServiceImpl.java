package com.wondersgroup.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.wondersgroup.mall.dao.PmsProductCategoryAttributeRelationDao;
import com.wondersgroup.mall.dao.PmsProductCategoryDao;
import com.wondersgroup.mall.dto.PmsProductCategoryParam;
import com.wondersgroup.mall.dto.PmsProductCategoryWithChildrenItem;
import com.wondersgroup.mall.mapper.PmsProductCategoryAttributeRelationMapper;
import com.wondersgroup.mall.mapper.PmsProductCategoryMapper;
import com.wondersgroup.mall.mapper.PmsProductMapper;
import com.wondersgroup.mall.model.*;
import com.wondersgroup.mall.service.PmsProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2422:42
 */
@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {
    @Autowired
    private PmsProductCategoryMapper pmsProductCategoryMapper;
    @Autowired
    private PmsProductCategoryDao pmsProductCategoryDao;
    @Autowired
    private PmsProductCategoryAttributeRelationDao pmsProductCategoryAttributeRelationDao;
    @Autowired
    private PmsProductMapper pmsProductMapper;
    @Autowired
    private PmsProductCategoryAttributeRelationMapper pmsProductCategoryAttributeRelationMapper;
    @Override
    public int create(PmsProductCategoryParam pmsProductCategoryParam) {
        PmsProductCategory pmsProductCategory=new PmsProductCategory();
        pmsProductCategory.setProductCount(0);
        BeanUtils.copyProperties(pmsProductCategoryParam, pmsProductCategory);
        setCategoryLevel(pmsProductCategory);
        int count=pmsProductCategoryMapper.insertSelective(pmsProductCategory);
        //创建帅选属性关联
        List<Long> productAttributeIdList=pmsProductCategoryParam.getProductAttributeIdList();
        if (!CollUtil.isEmpty(productAttributeIdList)){
            insertRelationList(pmsProductCategory.getId(), productAttributeIdList);
        }
        return count;
    }
    private void setCategoryLevel(PmsProductCategory pmsProductCategory){
        if (pmsProductCategory.getParentId()==null){
            pmsProductCategory.setLevel(0);
        }else {
           PmsProductCategory parentProductCategory= pmsProductCategoryMapper.selectByPrimaryKey(pmsProductCategory.getParentId());
           if (parentProductCategory!=null){
               pmsProductCategory.setLevel(parentProductCategory.getLevel()+1);
           }else {
               pmsProductCategory.setLevel(0);
           }

        }
    }

    /**
     * 批量插入商品分类与筛选属性关系表
     * @param productCategoryId 商品分类id
     * @param productAttributeList 相关商品筛选属性id集合
     */
    private void insertRelationList(Long productCategoryId,List<Long>productAttributeList){
        List<PmsProductCategoryAttributeRelation> list=new ArrayList<>();
        for (Long productId:productAttributeList){
            PmsProductCategoryAttributeRelation relation=new PmsProductCategoryAttributeRelation();
            relation.setProductCategoryId(productCategoryId);
            relation.setProductAttributeId(productId);
            list.add(relation);
        }
    pmsProductCategoryAttributeRelationDao.insertList(list);
    }

    @Override
    public int update(Long id, PmsProductCategoryParam pmsProductCategoryParam) {
        PmsProductCategory pmsProductCategory=new PmsProductCategory();
        pmsProductCategory.setId(id);
        BeanUtils.copyProperties(pmsProductCategoryParam, pmsProductCategory);
        setCategoryLevel(pmsProductCategory);
        //更新商品分类时要更新商品名称
        PmsProduct pmsProduct=new PmsProduct();
        pmsProduct.setProductCategoryName(pmsProductCategory.getName());
        PmsProductExample pmsProductExample=new PmsProductExample();
        pmsProductExample.createCriteria().andProductCategoryIdEqualTo(id);
        pmsProductMapper.updateByExampleSelective(pmsProduct, pmsProductExample);
        //同时更新筛选属性的信息
        if (!CollUtil.isEmpty(pmsProductCategoryParam.getProductAttributeIdList())){
            PmsProductCategoryAttributeRelationExample example=new PmsProductCategoryAttributeRelationExample();
            example.createCriteria().andProductCategoryIdEqualTo(id);
            pmsProductCategoryAttributeRelationMapper.deleteByExample(example);
            insertRelationList(id, pmsProductCategoryParam.getProductAttributeIdList());
        }else{
            PmsProductCategoryAttributeRelationExample relationExample = new PmsProductCategoryAttributeRelationExample();
            relationExample.createCriteria().andProductCategoryIdEqualTo(id);
            pmsProductCategoryAttributeRelationMapper.deleteByExample(relationExample);
        }
        return pmsProductCategoryMapper.updateByPrimaryKeySelective(pmsProductCategory);
    }

    @Override
    public List<PmsProductCategory> getList(Long parentId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductCategoryExample example=new PmsProductCategoryExample();
        PmsProductCategoryExample.Criteria criteria=example.createCriteria();
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId);
        return pmsProductCategoryMapper.selectByExample(example);
    }

    @Override
    public int delete(Long id) {
        return pmsProductCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PmsProductCategory getItem(Long id) {
        return pmsProductCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateNavStatus(List<Long> ids, Integer navStatus) {
        PmsProductCategory pmsProductCategory=new PmsProductCategory();
        pmsProductCategory.setNavStatus(navStatus);
        PmsProductCategoryExample example=new PmsProductCategoryExample();
        example.createCriteria().andIdIn(ids);
        return pmsProductCategoryMapper.updateByExampleSelective(pmsProductCategory, example);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        PmsProductCategory pmsProductCategory=new PmsProductCategory();
        pmsProductCategory.setShowStatus(showStatus);
        PmsProductCategoryExample example=new PmsProductCategoryExample();
        example.createCriteria().andIdIn(ids);
        return pmsProductCategoryMapper.updateByExampleSelective(pmsProductCategory, example);
    }

    @Override
    public List<PmsProductCategoryWithChildrenItem> listWithChildren() {
        return pmsProductCategoryDao.listWithChildren();
    }
}
