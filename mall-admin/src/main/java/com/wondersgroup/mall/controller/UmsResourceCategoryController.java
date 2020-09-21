package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.model.UmsResourceCategory;
import com.wondersgroup.mall.service.UmsResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lxg
 * @Description:
 * @date 2020/9/2023:41
 */
@Api(tags = "UmsResourceCategoryController",description = "后台资源分类管理")
@RestController
@RequestMapping(value = "resourceCategory")
public class UmsResourceCategoryController {
    @Autowired
    private UmsResourceCategoryService umsResourceCategoryService;
    @GetMapping(value = "/listAll")
    @ApiOperation("查询后台所有资源分类")
    public CommonResult<List<UmsResourceCategory>> listAll(){
        List<UmsResourceCategory> resourceCategoryList=umsResourceCategoryService.listAll();
        return CommonResult.success(resourceCategoryList);
    }
}
