/*
 * Copyright (c) 2020
 */

package com.platform.security.config.handler;/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 12:54 AM
 */

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 *@program: security-backend
 *@description: manager of access decision
 *@author: Tianshi Chen
 *@create: 2020-04-23 00:54
 */
@Component
public class CustomizeAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        // get the iterator
        for (ConfigAttribute ca : collection) {
            System.out.println("Config: " + ca);
            // the access want to have
            String needRole = ca.getAttribute();
            System.out.println("needRole: " + needRole);
            // what rights does the user have
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                System.out.println("GrantedAuthority: " + authority);
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("No Access!");
    }

    // with the configuration above, we support configAttributes
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    // with the configuration above, we support Class<>
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
