package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.model.UmsResourceCategory;
import com.wondersgroup.mall.service.UmsResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(value = "/create")
    @ApiOperation("添加后台资源分类")
    public CommonResult create(@RequestBody UmsResourceCategory umsResourceCategory){
        int count=umsResourceCategoryService.create(umsResourceCategory);
        if (count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }

    }
    @PostMapping(value = "/update/{id}")
    @ApiOperation("更新后台资源分类")
    public CommonResult update(@PathVariable Long id,@RequestBody UmsResourceCategory umsResourceCategory){
            int count=umsResourceCategoryService.update(id,umsResourceCategory);
            if (count>0){
                return CommonResult.success(count);
            }else {
                return CommonResult.failed();
            }
    }
    @PostMapping(value = "/delete/{id}")
    @ApiOperation("删除后台资源分类")
    public CommonResult delete(@PathVariable Long id){
        int count=umsResourceCategoryService.delete(id);
        if (count>0){
            return CommonResult.success(count);
        }else {
            return CommonResult.failed();
        }
    }
}
