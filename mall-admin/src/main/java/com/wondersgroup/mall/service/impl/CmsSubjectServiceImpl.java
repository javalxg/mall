package com.wondersgroup.mall.service.impl;

import com.wondersgroup.mall.mapper.CmsSubjectMapper;
import com.wondersgroup.mall.model.CmsSubject;
import com.wondersgroup.mall.model.CmsSubjectExample;
import com.wondersgroup.mall.service.CmsSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2422:59
 */
@Service
public class CmsSubjectServiceImpl implements CmsSubjectService {
    @Autowired
    private CmsSubjectMapper cmsSubjectMapper;
    @Override
    public List<CmsSubject> listAll() {
        return cmsSubjectMapper.selectByExample(new CmsSubjectExample());
    }

    @Override
    public List<CmsSubject> list(String keyword, Integer pageNum, Integer pageSize) {
        return null;
    }
}
