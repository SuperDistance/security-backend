/*
 * Copyright (c) 2020
 */

package com.platform.security.config.access;/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 12:28 AM
 */

import com.platform.security.entity.SysPermission;
import com.platform.security.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 *@program: security-backend
 *@description: to find out the list of apis given to a user
 *@author: Tianshi Chen
 *@create: 2020-04-23 00:28
 */
@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    SysPermissionService sysPermissionService;

    /**
    *@Description: to fetch a list of urls is permitted for current user
    *@Param: [o]
    *@return: java.util.Collection<org.springframework.security.access.ConfigAttribute>
    *@Author: Tianshi Chen
    *@date: 4/23/2020
    */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        // to get the urls
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        System.out.println("\nthe url to authorize: \n" + requestUrl);
        List<SysPermission> permissionList = sysPermissionService.selectListByPath(requestUrl);
        System.out.println("\nall permissions contains this url: \n" + permissionList);
        if ("/login".equals(requestUrl)) {
            return null;
        }
        else if(permissionList == null || permissionList.size() == 0) {
            // when there is no rules in database about this url, then it is open to alli
            return null;
        }
        String[] attributes = new String[permissionList.size()];
        for (int i = 0; i < permissionList.size(); i++) {
            attributes[i] = permissionList.get(i).getPermissionCode();
            System.out.println("permission attributes " + i + ": " + attributes[i]);
        }
        System.out.println("permissions: " + SecurityConfig.createList(attributes));
        return SecurityConfig.createList(attributes);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    // default return value is false, now we finish the function: getAttributes, support it
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
