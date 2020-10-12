package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonPage;
import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.dto.OmsOrderDetail;
import com.wondersgroup.mall.dto.OmsOrderQueryParam;
import com.wondersgroup.mall.model.OmsOrder;
import com.wondersgroup.mall.service.OmsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxg
 * @Description: 订单管理controller
 * @date 2020/10/1122:00
 */
@Api(tags = "OmsOrderController",description = "订单管理")
@RestController
@RequestMapping(value = "/order")
public class OmsOrderController {
    @Autowired
    private OmsOrderService omsOrderService;
    @ApiOperation("查询订单")
    @GetMapping(value = "/list")
    public CommonResult list(OmsOrderQueryParam omsOrderQueryParam, @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,@RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize){
        List<OmsOrder> omsOrder=omsOrderService.list(omsOrderQueryParam, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(omsOrder));
    }
    @GetMapping("/{id}")
    @ApiOperation("获取订单详情:订单信息、商品信息、操作记录")
    public CommonResult getItem(@PathVariable Long id){
        OmsOrderDetail omsOrderDetail=omsOrderService.detail(id);
        return CommonResult.success(omsOrderDetail);
    }
    @ApiOperation("备注订单")
    @PostMapping(value = "/update/note")
    public CommonResult updateNode(@RequestParam("id")Long id, @RequestParam("note")String note, @RequestParam("status")Integer status){
        int count=omsOrderService.updateNote(id, note, status);
        if (count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }
}
