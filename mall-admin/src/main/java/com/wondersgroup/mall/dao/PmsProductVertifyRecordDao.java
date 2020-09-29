package com.wondersgroup.mall.dao;

import com.wondersgroup.mall.model.PmsProductVertifyRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lxg
 * @Description:自定义商品审核日志管理dao
 * @date 2020/9/2622:57
 */
public interface PmsProductVertifyRecordDao {
    /**
     * 批量创建
     */
    int insertList(@Param("list") List<PmsProductVertifyRecord> list);
}
