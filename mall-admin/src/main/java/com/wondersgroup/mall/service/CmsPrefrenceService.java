package com.wondersgroup.mall.service;

import com.wondersgroup.mall.model.CmsPrefrenceArea;

import java.util.List;

/**
 * @author lxg
 * @Description: 商品优选service
 * @date 2020/9/2423:04
 */
public interface CmsPrefrenceService {
    /**
     * 获取所有优选专区
     */
    List<CmsPrefrenceArea> listAll();
}
