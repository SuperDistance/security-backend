/*
 * Copyright (c) 2020
 */

package com.platform.security.config.handler;/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 12:25 AM
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import java.io.IOException;

/**
 *@program: security-backend
 *@description: access interceptor
 *@author: Tianshi Chen
 *@create: 2020-04-23 00:25
 */
@Service
public class CustomizeAbstractSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    public void setMyAccessDecisionManager(CustomizeAccessDecisionManager customizeAccessDecisionManager) {
        super.setAccessDecisionManager(customizeAccessDecisionManager);
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    @Override
    public void doFilter (ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        invoke(fi);
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        // fi pass an url to deal with
        // invoke the MyInvocationSecurityMetadataSource.getAttributes(Object object) to get all the url list
        // Then use MyAccessDecisionManager.decide() to verify whether has the access
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            // do next filter
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }
}
