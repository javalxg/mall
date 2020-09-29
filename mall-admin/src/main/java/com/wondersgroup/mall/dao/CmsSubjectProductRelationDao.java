package com.wondersgroup.mall.dao;

import com.wondersgroup.mall.model.CmsSubjectProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lxg
 * @Description: 自定义商品和主题关联关系的操作
 * @date 2020/9/2611:16
 */
public interface CmsSubjectProductRelationDao {
    int insertList(@Param("list")List<CmsSubjectProductRelation> subjectProductRelationList);
}
