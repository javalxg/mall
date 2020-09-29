package com.wondersgroup.mall.service;

import com.wondersgroup.mall.model.UmsMemberLevel;

import java.util.List;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2423:30
 */
public interface UmsMemberLevelService {
    /**
     * 获取所有会员登录
     * @param defaultStatus 是否为默认会员
     */
    List<UmsMemberLevel> list(Integer defaultStatus);
}
