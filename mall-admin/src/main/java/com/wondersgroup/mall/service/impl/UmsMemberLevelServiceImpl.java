package com.wondersgroup.mall.service.impl;

import com.wondersgroup.mall.mapper.UmsMemberLevelMapper;
import com.wondersgroup.mall.model.UmsMemberLevel;
import com.wondersgroup.mall.model.UmsMemberLevelExample;
import com.wondersgroup.mall.service.UmsMemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2423:31
 */
@Service
public class UmsMemberLevelServiceImpl implements UmsMemberLevelService {
    @Autowired
    private UmsMemberLevelMapper umsMemberLevelMapper;
    @Override
    public List<UmsMemberLevel> list(Integer defaultStatus) {
        UmsMemberLevelExample example=new UmsMemberLevelExample();
        example.createCriteria().andDefaultStatusEqualTo(defaultStatus);
        return umsMemberLevelMapper.selectByExample(example);
    }
}
