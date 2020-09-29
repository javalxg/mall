package com.wondersgroup.mall.dao;

import com.wondersgroup.mall.dto.PmsProductResult;
import org.apache.ibatis.annotations.Param;

/**
 * @author lxg
 * @Description:自定义商品管理dao
 * @date 2020/9/2622:18
 */
public interface PmsProductDao {
    PmsProductResult getUpdateInfo(@Param("id")Long id);
}
