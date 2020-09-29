package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonPage;
import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.dto.PmsProductAttributeParam;
import com.wondersgroup.mall.dto.ProductAttrInfo;
import com.wondersgroup.mall.model.PmsProductAttribute;
import com.wondersgroup.mall.service.PmsProductAttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxg
 * @Description: 商品属性controller
 * @date 2020/9/2423:08
 */
@Api(value = "PmsProductAttributeController",description = "商品属性管理")
@RestController
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {
    @Autowired
    private PmsProductAttributeService pmsProductAttributeService;
    @ApiOperation("根据分类查询属性列表或者参数列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "type",value = "0表示属性 1表示参数",required = true,paramType = "query",dataType = "integer")})
    @GetMapping("/list/{cid}")
    public CommonResult<CommonPage<PmsProductAttribute>>getList(@PathVariable Long cid, @RequestParam(value = "type")Integer type, @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize, @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        List<PmsProductAttribute> list=pmsProductAttributeService.getList(cid, type, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPate(list));
    }
    @GetMapping(value = "/attrInfo/{productCategoryId}")
    @ApiOperation("根据商品分类的id获取商品属性和属性分类")
    public CommonResult<List<ProductAttrInfo>> getAttrInfo(@PathVariable Long productCategoryId){
       List<ProductAttrInfo> productAttrInfos= pmsProductAttributeService.getProductAttrInfo(productCategoryId);
        return CommonResult.success(productAttrInfos);
    }
    @PostMapping(value = "/create")
    @ApiOperation("商品属性添加")
    public CommonResult create(@RequestBody PmsProductAttributeParam pmsProductAttributeParam){
        int count=pmsProductAttributeService.create(pmsProductAttributeParam);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
    @ApiOperation("商品属性删除")
    @PostMapping("/delete")
    public  CommonResult delete(@RequestParam(value = "ids") List<Long> ids){
            pmsProductAttributeService.delete(ids);
            return null;
    }

}
