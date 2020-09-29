package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.model.PmsSkuStock;
import com.wondersgroup.mall.service.PmsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxg
 * @Description: sku库存
 * @date 2020/9/2621:48
 */
@RestController
@RequestMapping(value = "/sku")
@Api(tags = "PmsSkuStockController",description = "sku库存管理")
public class PmsSkuStockController {
    @Autowired
    private PmsSkuService pmsSkuService;
    @GetMapping("/{pid}")
    @ApiOperation("根据商品编号或者关键字模糊查库sku库存")
    public CommonResult<List<PmsSkuStock>> getList(@PathVariable  Long pid, @RequestParam(value = "keyword",required = false)String keyword){
        List<PmsSkuStock> list=pmsSkuService.getList(pid, keyword);
        return CommonResult.success(list);
    }
    @ApiOperation("批量更新库存信息")
    @RequestMapping(value ="/update/{pid}",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long pid,@RequestBody List<PmsSkuStock> skuStockList){
        int count = pmsSkuService.update(pid,skuStockList);
        if(count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }
}
