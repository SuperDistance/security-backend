/*
 * Copyright (c) 2020
 */

package com.platform.security.config.handler;
/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 8:28 PM
 */

import com.alibaba.fastjson.JSONObject;
import com.platform.security.common.utils.JwtTokenUtil;
import com.platform.security.common.utils.ResultTool;
import com.platform.security.entity.SysUser;
import com.platform.security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 *@program: security-backend
 *@description: to deal with login success & add token into response
 *@author: Tianshi Chen
 *@create: 2020-04-22 20:28
 */
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    SysUserService sysUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser sysUser = sysUserService.selectByName(userDetails.getUsername());
        // SecurityContextHolder.getContext().setAuthentication(authentication);

        sysUser.setLastLoginTime(LocalDateTime.now());
        sysUser.setUpdateTime(LocalDateTime.now());
        sysUser.setUpdateUser(sysUser.getId());
        System.out.println(sysUser.toString());
        sysUserService.updateById(sysUser);
        System.out.println(sysUserService.toString());
        //此处还可以进行一些处理，比如登录成功之后可能需要返回给前台当前用户有哪些菜单权限，
        //进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展

        System.out.println("UserDetails: " + userDetails);
        String token = jwtTokenUtil.generateToken(userDetails);

        //return json data
        renderToken(httpServletResponse, token);
    }
    /**
     * 渲染返回 token 页面,因为前端页面接收的都是Result对象，故使用application/json返回
     *
     * @param response
     * @throws IOException
     */
    public void renderToken(HttpServletResponse response, String token) throws IOException {
        //set the character set
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        //write user info into HttpServletResponse
        String str = JSONObject.toJSONString(ResultTool.success());
        response.addHeader(jwtTokenUtil.getHeader(), token);
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
