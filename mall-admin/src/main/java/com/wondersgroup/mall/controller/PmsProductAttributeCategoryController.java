package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonPage;
import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.dto.PmsProductAttributeCategoryItem;
import com.wondersgroup.mall.model.PmsProductAttributeCategory;
import com.wondersgroup.mall.service.PmsProductAttributeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxg
 * @Description: 商品属性分类controller
 * @date 2020/9/2423:18
 */
@Api(tags = "PmsProductAttributeCategoryController",description = "商品属性分类管理")
@RestController
@RequestMapping(value = "/productAttribute/category")
public class PmsProductAttributeCategoryController {
    @Autowired
    private PmsProductAttributeCategoryService pmsProductAttributeCategoryService;
    @ApiOperation("获取所有商品属性分类")
    @GetMapping("/list")
    public CommonResult<CommonPage<PmsProductAttributeCategory>> list(@RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize, @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        return CommonResult.success(CommonPage.restPate(pmsProductAttributeCategoryService.getList(pageSize, pageNum)));
    }
    @ApiOperation("获取所有商品属性分类及其以下属性")
    @GetMapping(value = "/list/withAttr")
    public CommonResult<List<PmsProductAttributeCategoryItem>> getWithAttr(){
        List<PmsProductAttributeCategoryItem> list=pmsProductAttributeCategoryService.getListWithAttr();
        return CommonResult.success(list);
    }
    @PostMapping(value = "/create")
    @ApiOperation("添加商品属性分类")
    public CommonResult create(@RequestParam String name){
        int count=pmsProductAttributeCategoryService.create(name);
        return CommonResult.success(count);
    }
}
