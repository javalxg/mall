package com.wondersgroup.mall.dao;

import com.wondersgroup.mall.model.PmsProductLadder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lxg
 * @Description: 阶梯价格
 * @date 2020/9/250:04
 */
public interface PmsProductLadderDao {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<PmsProductLadder> productLadderList);
}