package com.wondersgroup.mall.controller;

import cn.hutool.core.collection.CollUtil;
import com.wondersgroup.mall.common.api.CommonPage;
import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.dto.UmsAdminParam;
import com.wondersgroup.mall.dto.UmsLoginParam;
import com.wondersgroup.mall.model.UmsAdmin;
import com.wondersgroup.mall.model.UmsRole;
import com.wondersgroup.mall.service.UmsAdminService;
import com.wondersgroup.mall.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lxg
 * @Description: 用户controller
 * @date 2020/9/190:13
 */
@Controller
@Api(tags = "UmsAdminController",description = "后台用户管理模块")
@RequestMapping("admin")
public class UmsAdminController {
    @Autowired
    private UmsRoleService umsRoleService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsAdminService umsAdminService;
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UmsAdmin> register(@Validated @RequestBody UmsAdminParam umsAdminParam){
        UmsAdmin umsAdmin=umsAdminService.register(umsAdminParam);
        return CommonResult.success(umsAdmin);
    }
    @ApiOperation(value = "用户登录返回token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@Validated @RequestBody UmsLoginParam umsLoginParam){
        String token = umsAdminService.login(umsLoginParam.getUsername(), umsLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }
    @ApiOperation(value = "获取当前登录的用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getAdminInfo(Principal principal){
        if (principal==null){
            return CommonResult.unauthorized(null);
        }
        String username=principal.getName();
        UmsAdmin umsAdmin=umsAdminService.getAdminByUsername(username);
        Map<String,Object> data=new HashMap<>();
        data.put("username", umsAdmin.getUsername());
        data.put("menus", umsRoleService.getMenuList(umsAdmin.getId()));
        data.put("icon", umsAdmin.getIcon());
        List<UmsRole> roleList=umsAdminService.getRoleList(umsAdmin.getId());
        if(CollUtil.isNotEmpty(roleList)){
            List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
            data.put("roles",roles);
        }
        return CommonResult.success(data);
    }
    @ApiOperation(value = "登出功能")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout() {
        return CommonResult.success(null);
    }
    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
public CommonResult<CommonPage<UmsAdmin>>list(@RequestParam(value = "keyword",required = false) String keyword,
                                              @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                                              @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
    List<UmsAdmin> umsAdmins=umsAdminService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPate(umsAdmins));
}
    @ApiOperation("修改帐号状态")
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
public CommonResult updateUserStatus(@PathVariable(value = "id")Long id,@RequestParam(value = "status")Integer status){
    UmsAdmin umsAdmin=new UmsAdmin();
    umsAdmin.setStatus(status);
    int count=umsAdminService.update(id, umsAdmin);
    if (count>0){
        return CommonResult.success(count);
    }else{
        return CommonResult.failed();
    }
}
@ApiOperation("获取指定用户的角色")
@RequestMapping(value = "/role/{adminId}",method = RequestMethod.GET)
@ResponseBody
public CommonResult<List<UmsRole>> getRoleList(@PathVariable Long adminId){
    List<UmsRole> umsRoles=umsAdminService.getRoleList(adminId);
    return CommonResult.success(umsRoles);
}
@ApiOperation("给指定的用户分配角色")
@RequestMapping(value = "/role/update",method = RequestMethod.POST)
@ResponseBody
public CommonResult updateRole(@RequestParam Long adminId,@RequestParam("roleIds")List<Long> roleIds){
    int count=umsAdminService.updateRole(adminId, roleIds);
    if (count>=0){
        return CommonResult.success(count);
    }else{
        return CommonResult.failed();
    }
}

    @ApiOperation("获取指定用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<UmsAdmin> getItem(@PathVariable Long id) {
        UmsAdmin admin = umsAdminService.getItem(id);
        return CommonResult.success(admin);
    }
    @ApiOperation("修改指定用户的信息")
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id,@Validated @RequestBody UmsAdminParam umsAdminParam){
        UmsAdmin umsAdmin=new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        int count=umsAdminService.update(id, umsAdmin);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
@ApiOperation("删除指定用户信息")
@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
@ResponseBody
public CommonResult delete(@PathVariable Long id) {
    int count = umsAdminService.delete(id);
    if (count > 0) {
        return CommonResult.success(count);
    }
    return CommonResult.failed();
}
}
