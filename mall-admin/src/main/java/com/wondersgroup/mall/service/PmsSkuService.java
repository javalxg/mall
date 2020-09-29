package com.wondersgroup.mall.service;

import com.wondersgroup.mall.model.PmsSkuStock;

import java.util.List;

/**
 * @author lxg
 * @Description:查询sku库存service
 * @date 2020/9/2621:51
 */
public interface PmsSkuService {
    /**
     * 根据产品id和skuCode模糊搜索
     */
    List<PmsSkuStock> getList(Long pid, String keyword);

    /**
     * 批量更新商品库存信息
     */
    int update(Long pid, List<PmsSkuStock> skuStockList);
}
