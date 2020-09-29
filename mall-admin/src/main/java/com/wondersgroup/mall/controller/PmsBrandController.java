package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonPage;
import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.model.PmsBrand;
import com.wondersgroup.mall.service.PmsBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxg
 * @Description: 品牌类别controller
 * @date 2020/9/2422:16
 */
@Api(tags = "PmsBrandController",description = "品牌功能controller")
@RequestMapping(value = "/brand")
@RestController
public class PmsBrandController {
    @Autowired
    private PmsBrandService brandService;
    @ApiOperation(value = "根据品牌名称分页获取品牌列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> getList(@RequestParam(value = "keyword", required = false) String keyword,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<PmsBrand> brandList = brandService.listBrand(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPate(brandList));
    }
}
