package com.wondersgroup.mall.controller;

import com.wondersgroup.mall.common.api.CommonResult;
import com.wondersgroup.mall.dto.OssCallbackResult;
import com.wondersgroup.mall.dto.OssPolicyResult;
import com.wondersgroup.mall.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lxg
 * @Description: oss controller
 * @date 2020/9/2612:49
 */
@RequestMapping("/aliyun/oss")
@RestController
@Api(tags = "OssController",description = "Oss 管理")
public class OssController {
    @Autowired
    private OssService ossService;
    @ApiOperation(value = "oss上传签名生成")
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OssPolicyResult> policy() {
        OssPolicyResult result = ossService.policy();
        return CommonResult.success(result);
    }

    @ApiOperation(value = "oss上传成功回调")
    @RequestMapping(value = "callback", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<OssCallbackResult> callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return CommonResult.success(ossCallbackResult);
    }
}
