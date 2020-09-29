package com.wondersgroup.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.wondersgroup.mall.dao.*;
import com.wondersgroup.mall.dto.PmsProductParam;
import com.wondersgroup.mall.dto.PmsProductQueryParam;
import com.wondersgroup.mall.dto.PmsProductResult;
import com.wondersgroup.mall.mapper.*;
import com.wondersgroup.mall.model.*;
import com.wondersgroup.mall.service.PmsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2422:05
 */
@Service
public class PmsProductServiceImpl implements PmsProductService {
    private static final  Logger LOGGER= LoggerFactory.getLogger(PmsProductService.class);
    @Autowired
    private PmsProductMapper pmsProductMapper;
    @Autowired
    private PmsMemberPriceDao memberPriceDao;
    @Autowired
    private PmsProductLadderDao productLadderDao;
    @Autowired
    private PmsProductFullReductionDao pmsProductFullReductionDao;
    @Autowired
    private PmsSkuStockDao pmsSkuStockDao;
    @Autowired
    private PmsProductAttributeValueDao pmsProductAttributeValueDao;
    @Autowired
    private CmsSubjectProductRelationDao cmsSubjectProductRelationDao;
    @Autowired
    private CmsPrefrenceAreaProductRelationDao cmsPrefrenceAreaProductRelationDao;
    @Autowired
    private  PmsProductDao pmsProductDao;
    @Autowired
    private PmsMemberPriceMapper pmsMemberPriceMapper;
    @Autowired
    private PmsProductLadderMapper pmsProductLadderMapper;
    @Autowired
    private PmsProductFullReductionMapper pmsProductFullReductionMapper;
    @Autowired
    private PmsSkuStockMapper pmsSkuStockMapper;
    @Autowired
    private PmsProductAttributeValueMapper pmsProductAttributeValueMapper;

    @Autowired
    private CmsSubjectProductRelationMapper subjectProductRelationMapper;

    @Autowired
    private CmsPrefrenceAreaProductRelationMapper cmsPrefrenceAreaProductRelationMapper;
    @Autowired
    private  PmsProductVertifyRecordDao pmsProductVertifyRecordDao;

    @Override
    public int create(PmsProductParam productParam) {
        int count;
        //创建商品
        PmsProduct pmsProduct=productParam;
        pmsProduct.setId(null);
        pmsProductMapper.insertSelective(pmsProduct);
        //根据促销类型设置价格：会员价格、阶梯价格、满减价格
        Long productId=pmsProduct.getId();
        //会员价格
        relateAndInsert(memberPriceDao, productParam.getMemberPriceList(),productId);
        //阶梯价格
        relateAndInsert(productLadderDao, productParam.getProductLadderList(), productId);
        //满减价格
        relateAndInsert(pmsProductFullReductionDao, productParam.getProductFullReductionList(), productId);
        //处理sku的编码
        handleSkuStockCode(productParam.getSkuStockList(), productId);
        //添加sku库存的信息
        relateAndInsert(pmsSkuStockDao,productParam.getSkuStockList(),productId);
        //添加商品参数、添加自定义商品规格
        relateAndInsert(pmsProductAttributeValueDao, productParam.getProductAttributeValueList(), productId);
        //关联主题
        relateAndInsert(cmsSubjectProductRelationDao, productParam.getSubjectProductRelationList(), productId);
        //关联优选
        relateAndInsert(cmsSubjectProductRelationDao, productParam.getPrefrenceAreaProductRelationList(), productId);
        count=1;
        return count;
    }
    private void handleSkuStockCode(List<PmsSkuStock> skuStockList,Long productId){
        if (!CollUtil.isEmpty(skuStockList)){
            return;
        }
        for(int i=0;i<skuStockList.size();i++){
            PmsSkuStock skuStock = skuStockList.get(i);
            if(StringUtils.isEmpty(skuStock.getSkuCode())){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                //日期
                sb.append(sdf.format(new Date()));
                //四位商品id
                sb.append(String.format("%04d", productId));
                //三位索引id
                sb.append(String.format("%03d", i+1));
                skuStock.setSkuCode(sb.toString());
            }
        }

    }
    /**
     *建立和插入关系的表操作
     * @param dao 可以操作的dao
     * @param dataList 要插入的数据
     * @param productId 立关系的id
     */
    private void relateAndInsert(Object dao,List dataList,Long productId){
        try {
            if (CollUtil.isEmpty(dataList)){
                return;
            }
            for (Object item:dataList){
                Method setId=item.getClass().getMethod("setId", Long.class);
                setId.invoke(item, (Long)null);
                Method setProductId=item.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(item, productId);
            }
            Method insertList=dao.getClass().getMethod("insertList", List.class);
            insertList.invoke(dao, dataList);

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.warn("创建产品出错:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public PmsProductResult getUpdateInfo(Long id) {
        return pmsProductDao.getUpdateInfo(id);
    }

    @Override
    public int update(Long id, PmsProductParam productParam) {
        int count;
        //更新商品信息
        PmsProduct pmsProduct=productParam;
        pmsProduct.setId(id);
        pmsProductMapper.updateByPrimaryKeySelective(pmsProduct);
        //会员价格
        PmsMemberPriceExample pmsMemberPriceExample=new PmsMemberPriceExample();
        pmsMemberPriceExample.createCriteria().andProductIdEqualTo(id);
        pmsMemberPriceMapper.deleteByExample(pmsMemberPriceExample);
        relateAndInsert(memberPriceDao,productParam.getMemberPriceList(),id);
        //阶梯价格
        PmsProductLadderExample ladderExample = new PmsProductLadderExample();
        ladderExample.createCriteria().andProductIdEqualTo(id);
        pmsProductLadderMapper.deleteByExample(ladderExample);
        relateAndInsert(productLadderDao, productParam.getProductLadderList(), id);
        //满减价格
        PmsProductFullReductionExample fullReductionExample = new PmsProductFullReductionExample();
        fullReductionExample.createCriteria().andProductIdEqualTo(id);
        pmsProductFullReductionMapper.deleteByExample(fullReductionExample);
        relateAndInsert(pmsProductFullReductionDao, productParam.getProductFullReductionList(), id);
        //修改sku库存信息
        handleUpdateSkuStockList(id, productParam);
        //修改商品参数,添加自定义商品规格
        PmsProductAttributeValueExample productAttributeValueExample = new PmsProductAttributeValueExample();
        productAttributeValueExample.createCriteria().andProductIdEqualTo(id);
        pmsProductAttributeValueMapper.deleteByExample(productAttributeValueExample);
        relateAndInsert(pmsProductAttributeValueDao, productParam.getProductAttributeValueList(), id);
        //关联专题
        CmsSubjectProductRelationExample subjectProductRelationExample = new CmsSubjectProductRelationExample();
        subjectProductRelationExample.createCriteria().andProductIdEqualTo(id);
        subjectProductRelationMapper.deleteByExample(subjectProductRelationExample);
        relateAndInsert(cmsSubjectProductRelationDao, productParam.getSubjectProductRelationList(), id);
        //关联优选
        CmsPrefrenceAreaProductRelationExample prefrenceAreaExample = new CmsPrefrenceAreaProductRelationExample();
        prefrenceAreaExample.createCriteria().andProductIdEqualTo(id);
        cmsPrefrenceAreaProductRelationMapper.deleteByExample(prefrenceAreaExample);
        relateAndInsert(cmsPrefrenceAreaProductRelationDao, productParam.getPrefrenceAreaProductRelationList(), id);
        count = 1;
        return count;
    }

    @Override
    public List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample example=new PmsProductExample();
        PmsProductExample.Criteria criteria=example.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if (productQueryParam.getPublishStatus()!=null){
                criteria.andPublishStatusEqualTo(productQueryParam.getPublishStatus());
        }
        if (productQueryParam.getVerifyStatus()!=null){
            criteria.andVerifyStatusEqualTo(productQueryParam.getVerifyStatus());
        }
        if (!StringUtils.isEmpty(productQueryParam.getKeyword())){
            criteria.andNameLike("%"+productQueryParam.getKeyword()+"%");
        }
        if (!StringUtils.isEmpty(productQueryParam.getProductSn())){
            criteria.andProductSnEqualTo(productQueryParam.getProductSn());
        }
        if (productQueryParam.getBrandId() != null) {
            criteria.andBrandIdEqualTo(productQueryParam.getBrandId());
        }
        if (productQueryParam.getProductCategoryId() != null) {
            criteria.andProductCategoryIdEqualTo(productQueryParam.getProductCategoryId());
        }
        return pmsProductMapper.selectByExample(example);
    }

    @Override
    public int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail) {
        PmsProduct product = new PmsProduct();
        product.setVerifyStatus(verifyStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        List<PmsProductVertifyRecord> list = new ArrayList<>();
        int count = pmsProductMapper.updateByExampleSelective(product, example);
        //修改完审核状态后插入审核记录
        for (Long id : ids) {
            PmsProductVertifyRecord record = new PmsProductVertifyRecord();
            record.setProductId(id);
            record.setCreateTime(new Date());
            record.setDetail(detail);
            record.setStatus(verifyStatus);
            record.setVertifyMan("test");
            list.add(record);
        }
        pmsProductVertifyRecordDao.insertList(list);
        return count;
    }

    @Override
    public int updatePublishStatus(List<Long> ids, Integer publishStatus) {
        PmsProduct record=new PmsProductResult();
        record.setPublishStatus(publishStatus);
        PmsProductExample example=new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return pmsProductMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        PmsProduct record=new PmsProductResult();
        record.setRecommandStatus(recommendStatus);
        PmsProductExample example=new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return pmsProductMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        PmsProduct pmsProduct=new PmsProduct();
        pmsProduct.setNewStatus(newStatus);
        PmsProductExample example=new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return pmsProductMapper.updateByExampleSelective(pmsProduct, example);
    }

    @Override
    public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        PmsProduct record = new PmsProduct();
        record.setDeleteStatus(deleteStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return pmsProductMapper.updateByExampleSelective(record, example);
    }

    @Override
    public List<PmsProduct> list(String keyword) {
        PmsProductExample productExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if(!StringUtils.isEmpty(keyword)){
            criteria.andNameLike("%" + keyword + "%");
            productExample.or().andDeleteStatusEqualTo(0).andProductSnLike("%" + keyword + "%");
        }
        return pmsProductMapper.selectByExample(productExample);
    }

    public static void main(String[] args) {
        Integer productId=100;
        for (int i=0;i<10;i++){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            StringBuilder sb = new StringBuilder();
            //日期
            sb.append(sdf.format(new Date()));
            //四位商品id
            sb.append(String.format("%04d", productId));
            //三位索引id
            sb.append(String.format("%03d", i+1));
            System.out.println(sb.toString());
        }
    }
    private void handleUpdateSkuStockList(Long id,PmsProductParam pmsProductParam){
        //当前的sku信息
        List<PmsSkuStock> currSkuList=pmsProductParam.getSkuStockList();
        if (CollUtil.isEmpty(currSkuList)){
            //当前没有sku直接删除
            PmsSkuStockExample example=new PmsSkuStockExample();
            example.createCriteria().andProductIdEqualTo(id);
            pmsSkuStockMapper.deleteByExample(example);
            return;
        }
        //获取初始化sku信息
        PmsSkuStockExample pmsSkuStockExample=new PmsSkuStockExample();
        pmsSkuStockExample.createCriteria().andProductIdEqualTo(id);
        List<PmsSkuStock> oriStuList = pmsSkuStockMapper.selectByExample(pmsSkuStockExample);
        //获取新增的sku信息
        List<PmsSkuStock> insertSkuList=currSkuList.stream().filter(item->item.getId()==null).collect(Collectors.toList());
        //获取需要更新的sku信息
        List<PmsSkuStock> updateSkuList=currSkuList.stream().filter(item->item.getId()!=null).collect(Collectors.toList());
        List<Long>updateSkuIds=updateSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
        //获取需要删除的sku信息
        List<PmsSkuStock>removeSkuList=oriStuList.stream().filter(item->!updateSkuIds.contains(item.getId()))
                .collect(Collectors.toList());
        handleSkuStockCode(insertSkuList, id);
        handleSkuStockCode(updateSkuList, id);
        //新增sku
        if (!CollUtil.isEmpty(insertSkuList)){
            relateAndInsert(pmsSkuStockDao, insertSkuList, id);
        }
        //删除
        if (!CollUtil.isEmpty(removeSkuList)){
            List<Long> removeSkuIds = removeSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
            PmsSkuStockExample removeExample = new PmsSkuStockExample();
            removeExample.createCriteria().andIdIn(removeSkuIds);
            pmsSkuStockMapper.deleteByExample(removeExample);
        }
        //修改sku
        if(CollUtil.isNotEmpty(updateSkuList)){
            for (PmsSkuStock pmsSkuStock : updateSkuList) {
                pmsSkuStockMapper.updateByPrimaryKeySelective(pmsSkuStock);
            }
        }


    }
}
