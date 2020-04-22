/*
 * Copyright (c) 2020
 */

package com.platform.security.config.service;
/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 6:23 PM
 */

import com.platform.security.entity.SysPermission;
import com.platform.security.entity.SysUser;
import com.platform.security.service.SysPermissionService;
import com.platform.security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 *@program: security-backend
 *@description: to deal with the login of user
 *@author: Tianshi Chen
 *@create: 2020-04-22 18:23
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysPermissionService sysPermissionService;

    private boolean verify(Integer x) {return x.equals(1);}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || "".equals(username)) {
            throw new RuntimeException("User name can not be empty!");
        }
        //根据用户名查询用户
        SysUser sysUser = sysUserService.selectByName(username);
        if (sysUser == null) {
            throw new RuntimeException("User doesn't exist!");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        //获取该用户所拥有的权限
        List<SysPermission> sysPermissions = sysPermissionService.selectListByUser(sysUser.getId());
        // 声明用户授权
        sysPermissions.forEach(sysPermission -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getPermissionCode());
            grantedAuthorities.add(grantedAuthority);
        });

        return new User(sysUser.getAccount(), sysUser.getPassword(), verify(sysUser.getEnabled()), verify(sysUser.getAccountNonExpired()),
                verify(sysUser.getCredentialsNonExpired()), verify(sysUser.getAccountNonLocked()), grantedAuthorities);
    }
}

