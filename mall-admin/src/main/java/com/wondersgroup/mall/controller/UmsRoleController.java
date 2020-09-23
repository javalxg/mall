package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonPage;
import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.model.UmsMenu;
import com.wondersgroup.mall.model.UmsResource;
import com.wondersgroup.mall.model.UmsRole;
import com.wondersgroup.mall.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lxg
 * @Description: 后台角色controller
 * @date 2020/9/2015:06
 */
@RestController
@RequestMapping("/role")
@Api(description = "后台用户角色管理",tags = "UmsRoleController")
public class UmsRoleController {
    @Autowired
    private UmsRoleService umsRoleService;
    @ApiOperation("根据角色名称分页获取角色列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
public CommonResult<CommonPage<UmsRole>> list(@RequestParam(value = "keyword",required = false)String keyword,
                                              @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum){
    List<UmsRole> list= umsRoleService.list(keyword, pageSize,pageNum);
    return CommonResult.success(CommonPage.restPate(list));
     }
     @ApiOperation("添加角色")
     @PostMapping("/create")
     public CommonResult create(@RequestBody UmsRole umsRole){
           int count= umsRoleService.create(umsRole);
            if (count>0){
                return CommonResult.success(count);
            }
            return CommonResult.failed();
    }
    @ApiOperation("修改角色的状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id,@RequestParam(value = "status")Integer status){
        UmsRole umsRole=new UmsRole();
        umsRole.setStatus(status);
        int count=umsRoleService.update(id, umsRole);
        if (count>0){
            return CommonResult.success(count);
        }else {
            return CommonResult.failed();
        }
    }
    @GetMapping("/listMenu/{roleId}")
    @ApiOperation("获取角色相关菜单")
    public CommonResult<List<UmsMenu>> listMenu(@PathVariable Long roleId){
        List<UmsMenu> list=umsRoleService.listMenu(roleId);
        return CommonResult.success(list);
    }
    @ApiOperation("给角色分配菜单")
    @RequestMapping(value = "/allocMenu", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult allocMenu(@RequestParam Long roleId,@RequestParam List<Long> menuIds){
        int count=umsRoleService.allocMenu(roleId, menuIds);
        return CommonResult.success(count);
    }
    @ApiOperation("获取角色相关的资源")
    @GetMapping("/listResource/{roleId}")
    public CommonResult<List<UmsResource>>listResource(@PathVariable Long roleId){
            List<UmsResource> list=umsRoleService.listResource(roleId);
            return CommonResult.success(list);
    }
    @ApiOperation("给角色分配资源")
    @PostMapping("/allocResource")
    public CommonResult allocResource(@RequestParam Long roleId,@RequestParam List<Long> resourceIds){
            int count=umsRoleService.allocResource(roleId, resourceIds);
            return CommonResult.success(count);
    }
    @ApiOperation("修改角色")
    @PostMapping("/update/{roleId}")
    public CommonResult update(@PathVariable Long roleId,@RequestBody UmsRole umsRole){
        int count=umsRoleService.update(roleId, umsRole);
        if (count>0){
            return CommonResult.success(count);
        }else {
            return CommonResult.failed();
        }
    }
    @ApiOperation("批量删除角色")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam("ids")List<Long> ids){
       int count= umsRoleService.delete(ids);
       if (count>0){
           return CommonResult.success(count);
       }else{
           return CommonResult.failed();
       }
    }
    @ApiOperation("获取所有角色")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsRole>> listAll() {
        List<UmsRole> roleList = umsRoleService.list();
        return CommonResult.success(roleList);
    }
}
