package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.dto.UmsMenuNode;
import com.wondersgroup.mall.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
