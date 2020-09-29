package com.wondersgroup.mall.service.impl;

import com.wondersgroup.mall.mapper.CmsPrefrenceAreaMapper;
import com.wondersgroup.mall.model.CmsPrefrenceArea;
import com.wondersgroup.mall.model.CmsPrefrenceAreaExample;
import com.wondersgroup.mall.service.CmsPrefrenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2423:04
 */
@Service
public class CmsPrefrenceServiceImpl implements CmsPrefrenceService {
    @Autowired
    private CmsPrefrenceAreaMapper cmsPrefrenceAreaMapper;
    @Override
    public List<CmsPrefrenceArea> listAll() {
        return cmsPrefrenceAreaMapper.selectByExample(new CmsPrefrenceAreaExample());
    }
}
