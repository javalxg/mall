package com.wondersgroup.mall.dao;

import com.wondersgroup.mall.model.PmsProductAttributeValue;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lxg
 * @Description: 商品参数，商品自定义规格dao
 * @date 2020/9/2611:12
 */
public interface PmsProductAttributeValueDao {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<PmsProductAttributeValue> productAttributeValueList);
}
