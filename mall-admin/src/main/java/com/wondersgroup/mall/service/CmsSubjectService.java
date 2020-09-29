package com.wondersgroup.mall.service;

import com.wondersgroup.mall.model.CmsSubject;

import java.util.List;

/**
 * @author lxg
 * @Description: 商品专题service
 * @date 2020/9/2422:58
 */
public interface CmsSubjectService {
    /**
     * 查询所有专题
     */
    List<CmsSubject> listAll();

    /**
     * 分页查询专题
     */
    List<CmsSubject> list(String keyword, Integer pageNum, Integer pageSize);
}
