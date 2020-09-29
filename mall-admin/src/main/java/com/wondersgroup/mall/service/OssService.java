package com.wondersgroup.mall.service;

import com.wondersgroup.mall.dto.OssCallbackResult;
import com.wondersgroup.mall.dto.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lxg
 * @Description: oss上传管理service
 * @date 2020/9/2612:51
 */
public interface OssService {
    /**
     * oss上传策略生成
     */
    OssPolicyResult policy();
    /**
     * oss上传成功回调
     */
    OssCallbackResult callback(HttpServletRequest request);
}
