package com.wondersgroup.mall.dao;

import com.wondersgroup.mall.model.PmsProductFullReduction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lxg
 * @Description: 满减价格
 * @date 2020/9/250:06
 */
public interface PmsProductFullReductionDao {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<PmsProductFullReduction> productFullReductionList);
}
