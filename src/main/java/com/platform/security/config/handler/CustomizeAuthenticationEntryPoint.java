/*
 * Copyright (c) 2020
 */

package com.platform.security.config.handler;/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 8:10 PM
 */

import com.alibaba.fastjson.JSON;
import com.platform.security.common.entity.JsonResult;
import com.platform.security.common.enums.ResultCode;
import com.platform.security.common.utils.ResultTool;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *@program: security-backend
 *@description: to deal with anonymous user access; return a USER NOT LOGIN error
 *@author: Tianshi Chen
 *@create: 2020-04-22 20:10
 */
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        JsonResult jsonResult = ResultTool.fail(ResultCode.USER_NOT_LOGIN);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(jsonResult));
    }
}
