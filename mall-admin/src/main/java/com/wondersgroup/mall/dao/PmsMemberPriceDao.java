package com.wondersgroup.mall.dao;

import com.wondersgroup.mall.model.PmsMemberPrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lxg
 * @Description: 自定义会员价格dao
 * @date 2020/9/250:02
 */
public interface PmsMemberPriceDao {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<PmsMemberPrice> memberPriceList);
}