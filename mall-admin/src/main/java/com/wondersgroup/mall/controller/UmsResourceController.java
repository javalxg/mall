package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.model.UmsResource;
import com.wondersgroup.mall.service.UmsResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lxg
 * @Description:后台资源管理
 * @date 2020/9/2023:50
 */
@Api(tags = "UmsResourceController",description = "后台资源管理")
@RequestMapping(value = "resource")
@RestController
public class UmsResourceController {
    @Autowired
    private UmsResourceService umsResourceService;
    @ApiOperation("查询后台所有的资源")
    @GetMapping("/listAll")
    public CommonResult<List<UmsResource>> listAll(){
        List<UmsResource> list=umsResourceService.listAll();
        return CommonResult.success(list);
    }
}
