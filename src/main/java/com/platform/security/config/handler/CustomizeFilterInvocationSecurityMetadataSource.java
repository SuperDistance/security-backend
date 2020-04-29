/*
 * Copyright (c) 2020
 */

package com.platform.security.config.handler;/**
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
        System.out.println("\nlist of urls: \n" + requestUrl);
        // find out the permission list of one specific api/url
        // find out the first part of url like /xxx in /xxx/yyy
//        String save = requestUrl;
//        if (requestUrl.contains("?")) {
//            requestUrl = requestUrl.split("\\?")[0];
//        }
//        requestUrl = "/" + requestUrl.split("/")[1];
//        System.out.println("\ntrimming : \n" + requestUrl);

        // requestUrl = save;

        List<SysPermission> permissionList = sysPermissionService.selectListByPath(requestUrl);
        System.out.println("\ntrusted api for this user: \n" + permissionList);
        if ("/login".equals(requestUrl)) {
            return null;
        }
        else if(permissionList == null || permissionList.size() == 0) {
            // when there is no rules in database about this url, then it is open to all
            return null;
        }
        String[] attributes = new String[permissionList.size()];
        for (int i = 0; i < permissionList.size(); i++) {
            attributes[i] = permissionList.get(i).getPermissionCode();
            System.out.println("attributes" + i + ": " + attributes[0]);
        }
        System.out.println("Answer" + SecurityConfig.createList(attributes));
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
