package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonPage;
import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.model.UmsResource;
import com.wondersgroup.mall.security.component.DynamicSecurityMetadataSource;
import com.wondersgroup.mall.service.UmsResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;
    @Autowired
    private UmsResourceService umsResourceService;
    @ApiOperation("查询后台所有的资源")
    @GetMapping("/listAll")
    public CommonResult<List<UmsResource>> listAll(){
        List<UmsResource> list=umsResourceService.listAll();
        return CommonResult.success(list);
    }
    @ApiOperation("分页模糊查询后台资源")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
public CommonResult<CommonPage<UmsResource>> list(@RequestParam(required = false)Long categoryId,
                                                  @RequestParam(required = false)String nameKeyword,
                                                  @RequestParam(required = false)String urlKeyword,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
    List<UmsResource> umsResources=umsResourceService.list(categoryId, nameKeyword, urlKeyword, pageSize, pageNum);
    return CommonResult.success(CommonPage.restPate(umsResources));
}
@ApiOperation("添加后台资源")
@PostMapping("/create")
    public CommonResult create(@RequestBody UmsResource umsResource){
        int count=umsResourceService.create(umsResource);
    dynamicSecurityMetadataSource.loadDataSource();

    if (count > 0) {
        return CommonResult.success(count);
    } else {
        return CommonResult.failed();
    }
}
    @ApiOperation("修改后台资源")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id,
                               @RequestBody UmsResource umsResource) {
        int count = umsResourceService.update(id, umsResource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
    @ApiOperation("根据ID获取资源详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<UmsResource> getItem(@PathVariable Long id) {
        UmsResource umsResource = umsResourceService.getItem(id);
        return CommonResult.success(umsResource);
    }
    @ApiOperation("根据ID删除后台资源")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        int count = umsResourceService.delete(id);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return CommonResult.success(count);
        } else {
            return CommonResult.failed();
        }
    }
}
