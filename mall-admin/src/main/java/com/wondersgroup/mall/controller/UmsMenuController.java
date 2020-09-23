package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonPage;
import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.dto.UmsMenuNode;
import com.wondersgroup.mall.model.UmsMenu;
import com.wondersgroup.mall.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxg
 * @Description:菜单controller
 * @date 2020/9/2015:29
 */
@RestController
@RequestMapping(value = "/menu")
@Api(tags = "UmsMenuController",description = "后台菜单管理")
public class UmsMenuController {
    @Autowired
    private MenuService menuService;
    @ApiOperation("树形结构返回所有菜单列表")
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    public CommonResult<List<UmsMenuNode>> treeList(){
        List<UmsMenuNode> list=menuService.treeList();
        return CommonResult.success(list);
    }
    @ApiOperation("分页查询后台菜单")
    @GetMapping("/list/{parentId}")
    public CommonResult<CommonPage<UmsMenu>> list(@PathVariable Long parentId, @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum ){
        List<UmsMenu> list=menuService.list(parentId, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPate(list));
    }
    @ApiOperation("修改菜单显示状态")
    @PostMapping(value = "/updateHidden/{id}")
    public CommonResult updateHidden(@PathVariable Long id,@RequestParam(value = "hidden")Integer hidden){
        int count=menuService.updateHidden(id, hidden);
        if (count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }
    @ApiOperation("根据菜单id获取详情")
    @GetMapping(value = "/{id}")
    public CommonResult<UmsMenu> get(@PathVariable Long id){
        UmsMenu umsMenu=menuService.getIten(id);
        return CommonResult.success(umsMenu);
    }
    @ApiOperation("修改后台菜单")
    @PostMapping(value = "/update/{id}")
    public CommonResult update(@PathVariable Long id,@RequestBody UmsMenu umsMenu){
        int count=menuService.update(id, umsMenu);
        if (count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }
    @ApiOperation("添加后台菜单")
    @PostMapping(value = "/create")
    public CommonResult ctreate(@RequestBody UmsMenu umsMenu){
        int count=menuService.create(umsMenu);
        if (count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }
    @ApiOperation("根据菜单id删除后台菜单")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id){
        int count=menuService.delete(id);
        if (count>0){
            return CommonResult.success(count);
        }else{
            return CommonResult.failed();
        }
    }

}
