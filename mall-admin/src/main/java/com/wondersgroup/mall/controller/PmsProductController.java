package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonPage;
import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.dto.PmsProductParam;
import com.wondersgroup.mall.dto.PmsProductQueryParam;
import com.wondersgroup.mall.model.PmsProduct;
import com.wondersgroup.mall.service.PmsProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxg
 * @Description: 商品管理
 * @date 2020/9/2421:56
 */
@RestController
@Api(tags = "PmsProductController",description = "商品管理")
@RequestMapping("/product")
public class PmsProductController {
    @Autowired
    private PmsProductService pmsProductService;
    @ApiOperation("查询商品")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public CommonResult<CommonPage<PmsProduct>> list(PmsProductQueryParam pmsProductQueryParam,
                                                     @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        List<PmsProduct> list=pmsProductService.list(pmsProductQueryParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPate(list));
    }
    @ApiOperation("创建商品")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public CommonResult create(@RequestBody PmsProductParam pmsProductParam){
        int count=pmsProductService.create(pmsProductParam);
        return CommonResult.success(count);
    }
    @ApiOperation("批量上下架")
    @PostMapping(value = "update/publishStatus")
    public CommonResult updatePublicshStatus(@RequestParam(value = "ids")List<Long> ids,@RequestParam(value = "publishStatus")Integer publishStatus){
        int count=pmsProductService.updatePublishStatus(ids, publishStatus);
        if (count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }
    @ApiOperation("批量设为新品")
    @PostMapping(value = "update/newStatus")
    public CommonResult updateNewStatus(@RequestParam(value = "ids")List<Long> ids,@RequestParam(value = "newStatus")Integer newStatus){
        int count=pmsProductService.updateNewStatus(ids, newStatus);
        if (count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }
    @ApiOperation("批量推荐商品")
    @PostMapping(value = "update/recommendStatus")
    public CommonResult updateRecommendStatus(@RequestParam(value = "ids")List<Long> ids,@RequestParam(value = "recommendStatus")Integer recommendStatus){
        int count=pmsProductService.updateRecommendStatus(ids, recommendStatus);
        if (count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }
    @GetMapping(value = "/updateInfo/{id}")
    @ApiOperation("根据商品id获取商品编辑信息")
    public CommonResult<PmsProduct>getUpdateInfo(@PathVariable Long id){
        PmsProduct pmsProduct=pmsProductService.getUpdateInfo(id);
        return CommonResult.success(pmsProduct);
    }
    @PostMapping(value = "/update/{id}")
    @ApiOperation("根据商品id修改商品信息")
    public CommonResult update(@PathVariable Long id,@RequestBody PmsProductParam pmsProductParam){
        int count=pmsProductService.update(id, pmsProductParam);
        if (count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }
    @ApiOperation("批量修改删除状态")
    @RequestMapping(value = "/update/deleteStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateDeleteStatus(@RequestParam("ids") List<Long> ids,
                                           @RequestParam("deleteStatus") Integer deleteStatus) {
        int count = pmsProductService.updateDeleteStatus(ids, deleteStatus);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
    @ApiOperation("根据商品名称或货号模糊查询")
    @RequestMapping(value = "/simpleList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<PmsProduct>> getList(String keyword) {
        List<PmsProduct> productList = pmsProductService.list(keyword);
        return CommonResult.success(productList);
    }

    @ApiOperation("批量修改审核状态")
    @RequestMapping(value = "/update/verifyStatus", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateVerifyStatus(@RequestParam("ids") List<Long> ids,
                                           @RequestParam("verifyStatus") Integer verifyStatus,
                                           @RequestParam("detail") String detail) {
        int count = pmsProductService.updateVerifyStatus(ids, verifyStatus, detail);
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
}
