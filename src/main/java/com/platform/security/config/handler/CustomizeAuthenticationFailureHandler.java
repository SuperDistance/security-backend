/*
 * Copyright (c) 2020
 */

package com.platform.security.config.handler;
/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 9:52 PM
 */

import com.alibaba.fastjson.JSON;
import com.platform.security.common.entity.JsonResult;
import com.platform.security.common.enums.ResultCode;
import com.platform.security.common.utils.ResultTool;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *@program: security-backend
 *@description: to deal with when login process failed
 *@author: Tianshi Chen
 *@create: 2020-04-22 21:52
 */
@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        JsonResult result = null;
        if (e instanceof AccountExpiredException) {
            // when account expired
            result = ResultTool.fail(ResultCode.USER_ACCOUNT_EXPIRED);
        } else if (e instanceof BadCredentialsException) {
            // when password incorrect
            result = ResultTool.fail(ResultCode.USER_CREDENTIALS_ERROR);
        } else if (e instanceof CredentialsExpiredException) {
            // when password expired
            result = ResultTool.fail(ResultCode.USER_CREDENTIALS_EXPIRED);
        } else if (e instanceof DisabledException) {
            // when user account disabled
            result = ResultTool.fail(ResultCode.USER_ACCOUNT_DISABLE);
        } else if (e instanceof LockedException) {
            // when user account locked
            result = ResultTool.fail(ResultCode.USER_ACCOUNT_LOCKED);
        } else if (e instanceof InternalAuthenticationServiceException) {
            // when user not exist
            result = ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST);
        }else{
            // other errors
            result = ResultTool.fail(ResultCode.COMMON_FAIL);
        }
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        // character setting
        httpServletResponse.setContentType("text/json;charset=utf-8");
        //put into HttpServletResponse return to frontend
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
