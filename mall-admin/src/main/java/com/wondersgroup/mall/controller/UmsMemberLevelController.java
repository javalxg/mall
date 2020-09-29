package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.model.UmsMemberLevel;
import com.wondersgroup.mall.service.UmsMemberLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2423:29
 */
@Api(description = "会员等级管理",tags = "UmsMemberLevelController")
@RequestMapping("/memberLevel")
@RestController
public class UmsMemberLevelController {
    @Autowired
    private UmsMemberLevelService umsMemberLevelService;
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation("查询所有会员等级")
    public CommonResult<List<UmsMemberLevel>> list(@RequestParam("defaultStatus") Integer defaultStatus){
        return CommonResult.success(umsMemberLevelService.list(defaultStatus));
    }
}
