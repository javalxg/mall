package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.model.CmsPrefrenceArea;
import com.wondersgroup.mall.service.CmsPrefrenceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lxg
 * @Description:商品优选controller
 * @date 2020/9/2423:02
 */
@Api(tags = "CmsPrefrenceController",description = "商品优选管理")
@RestController
@RequestMapping("prefrenceArea")
public class CmsPrefrenceController {
    @Autowired
    private CmsPrefrenceService cmsPrefrenceService;
    @ApiOperation("查询所有商品优选")
    @GetMapping(value = "/listAll")
    public CommonResult<List<CmsPrefrenceArea>> listAll(){
        return CommonResult.success(cmsPrefrenceService.listAll());
    }
}
