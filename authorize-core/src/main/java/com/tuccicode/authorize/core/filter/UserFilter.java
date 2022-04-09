package com.tuccicode.authorize.core.filter;

import com.alibaba.fastjson.JSON;
import com.tuccicode.authorize.common.exception.AuthorizeBizCode;
import com.tuccicode.authorize.core.authc.Authenticator;
import com.tuccicode.raccoon.dto.Response;
import org.springframework.util.AntPathMatcher;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tucci.lee
 */
public class UserFilter implements Filter {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final Authenticator authenticator;
    private List<String> excludeUrls;

    public UserFilter(Authenticator authenticator) {
        excludeUrls = new ArrayList<>();
        this.authenticator = authenticator;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        for (String excludeUrl : excludeUrls) {
            if (antPathMatcher.match(excludeUrl, request.getRequestURI())) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        try {
            if (!isAccessAllowed(request, response)) {
                onAccessDenied(request, response);
                return;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }

        filterChain.doFilter(request, response);
    }

    protected boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return authenticator.isAuthenticated();
    }

    protected void onAccessDenied(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        String result = JSON.toJSONString(Response.fail(AuthorizeBizCode.UNAUTHENTICATED));
        response.getWriter().print(result);
    }

    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        if (excludeUrls == null || excludeUrls.isEmpty()) {
            throw new IllegalArgumentException("excludeUrls must not be empty");
        }
        this.excludeUrls = excludeUrls;
    }
}
