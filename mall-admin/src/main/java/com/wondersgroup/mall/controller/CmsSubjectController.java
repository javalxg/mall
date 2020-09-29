package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.model.CmsSubject;
import com.wondersgroup.mall.service.CmsSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lxg
 * @Description: 商品专题controller
 * @date 2020/9/2422:57
 */
@Api(tags = "CmsSubjectController",description = "商品专题管理")
@RequestMapping(value = "subject")
@RestController
public class CmsSubjectController {
    @Autowired
    private CmsSubjectService cmsSubjectService;
    @ApiOperation("获取商品所有专题")
    @GetMapping("/listAll")
    public CommonResult<List<CmsSubject>> listAll(){
        return CommonResult.success(cmsSubjectService.listAll());
    }
}
