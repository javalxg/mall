package com.wondersgroup.mall.dao;

import com.wondersgroup.mall.model.CmsPrefrenceAreaProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lxg
 * @Description:自定义优选和商品之间的操作
 * @date 2020/9/2611:19
 */
public interface CmsPrefrenceAreaProductRelationDao {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList);
}
