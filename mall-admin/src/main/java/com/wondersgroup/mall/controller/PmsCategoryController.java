package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonPage;
import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.dto.PmsProductCategoryParam;
import com.wondersgroup.mall.dto.PmsProductCategoryWithChildrenItem;
import com.wondersgroup.mall.model.PmsProductCategory;
import com.wondersgroup.mall.service.PmsProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxg
 * @Description: 商品分类controller
 * @date 2020/9/2422:35
 */
@RestController
@Api(tags = "PmsCategoryController",description = "商品分类管理")
@RequestMapping(value = "/productCategory")
public class PmsCategoryController {
    @Autowired
    private PmsProductCategoryService pmsProductCategoryService;
    @ApiOperation("查询所有一级分类及所有子类")
    @GetMapping(value = "/list/withChildren")
    public CommonResult<List<PmsProductCategoryWithChildrenItem>> listWithChildren(){
        List<PmsProductCategoryWithChildrenItem> list=pmsProductCategoryService.listWithChildren();
        return CommonResult.success(list);
    }
    @ApiOperation("分页查询商品分类")
    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
    public CommonResult<CommonPage<PmsProductCategory>> getList(@PathVariable Long parentId,
                                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<PmsProductCategory> productCategoryList = pmsProductCategoryService.getList(parentId, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPate(productCategoryList));
    }
    @PostMapping(value = "/create")
    @ApiOperation("添加商品分类")
    public CommonResult create(@RequestBody PmsProductCategoryParam pmsProductCategoryParam){
        int count= pmsProductCategoryService.create(pmsProductCategoryParam);
        return CommonResult.success(count);
    }
    @GetMapping(value = "/{id}")
    @ApiOperation("根据商品id获取商品分类")
    public CommonResult<PmsProductCategory>getId(@PathVariable Long id){
                 PmsProductCategory pmsProductCategory=pmsProductCategoryService.getItem(id);
        return CommonResult.success(pmsProductCategory);
    }
    @ApiOperation("修改商品分类")
    @PostMapping(value = "/update/{id}")
    public CommonResult update(@PathVariable Long id,@Validated @RequestBody PmsProductCategoryParam pmsProductCategoryParam){
        int count=pmsProductCategoryService.update(id, pmsProductCategoryParam);
        if (count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }

    }
    @ApiOperation("修改商品分类导航栏状态")
    @PostMapping(value = "/update/navStatus")
    public CommonResult updateNavStatus(@RequestParam(value = "ids")List<Long> ids,@RequestParam(value = "navStatus")Integer navStatus){
        int count=pmsProductCategoryService.updateNavStatus(ids, navStatus);
        if (count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }
    @ApiOperation("修改商品分类显示状态")
    @PostMapping(value = "/update/showStatus")
    public CommonResult updateShowStatus(@RequestParam(value = "ids")List<Long> ids,@RequestParam(value = "showStatus")Integer showStatus){
        int count=pmsProductCategoryService.updateShowStatus(ids, showStatus);
        if (count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }
    @ApiOperation("根据商品分类主键删除商品分类")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id){
        int count=pmsProductCategoryService.delete(id);
        if (count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }

}
