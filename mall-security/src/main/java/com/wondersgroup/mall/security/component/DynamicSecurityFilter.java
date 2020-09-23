package com.wondersgroup.mall.security.component;

import com.wondersgroup.mall.security.config.IgnoreUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author lxg
 * @Description: 动态权限过滤器，用于实现基于路径的动态权限过滤
 * @date 2020/9/2123:44
 */
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {
    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;
    @Autowired
    private IgnoreUrlConfig ignoreUrlConfig;
    @Autowired
    public void setMyAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager){
        super.setAccessDecisionManager(dynamicAccessDecisionManager);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        FilterInvocation filterInvocation=new FilterInvocation(servletRequest,servletResponse,filterChain);
        //OPTIONS 请求直接放行
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())){
                filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
                return;
        }
        //白名单请求直接放行
        PathMatcher pathMatcher=new AntPathMatcher();
        for (String url:ignoreUrlConfig.getUrls()){
            if (pathMatcher.match(url, request.getRequestURI())){
                filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
                return;
            }
        }
        //此处会调用AccessDecisionManager中的decide方法进行鉴权操作
        InterceptorStatusToken token=super.beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        }finally {
            super.afterInvocation(token, null);
        }

    }

    @Override
    public void destroy() {
    }
    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetadataSource;
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
