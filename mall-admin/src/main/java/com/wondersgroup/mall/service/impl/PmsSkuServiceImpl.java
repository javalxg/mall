package com.wondersgroup.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wondersgroup.mall.dao.PmsSkuStockDao;
import com.wondersgroup.mall.mapper.PmsSkuStockMapper;
import com.wondersgroup.mall.model.PmsSkuStock;
import com.wondersgroup.mall.model.PmsSkuStockExample;
import com.wondersgroup.mall.service.PmsSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2621:52
 */
@Service
public class PmsSkuServiceImpl implements PmsSkuService {
    @Autowired
    private PmsSkuStockMapper pmsSkuStockMapper;
    @Autowired
    private PmsSkuStockDao pmsSkuStockDao;
    @Override
    public List<PmsSkuStock> getList(Long pid, String keyword) {
        PmsSkuStockExample example= new PmsSkuStockExample();
        PmsSkuStockExample.Criteria criteria=example.createCriteria();
        criteria.andProductIdEqualTo(pid);
        if (!StrUtil.isEmpty(keyword)){
            criteria.andSkuCodeLike("%"+keyword+"%");
        }

        return pmsSkuStockMapper.selectByExample(example);
    }

    @Override
    public int update(Long pid, List<PmsSkuStock> skuStockList) {
        return pmsSkuStockDao.replaceList(skuStockList);
    }
}
