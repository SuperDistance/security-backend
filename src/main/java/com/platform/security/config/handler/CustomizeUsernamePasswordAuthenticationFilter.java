/*
 * Copyright (c) 2020
 */

package com.platform.security.config.handler;
/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 12:17 PM
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *@program: JWTtest
 *@description: support json form and support further customize
 *@author: Tianshi Chen
 *@create: 2020-04-27 12:17
 */
public class CustomizeUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    // filter the url of /login with POST
    public CustomizeUsernamePasswordAuthenticationFilter () {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        // obtain username and password from json
        String body = StreamUtils.copyToString(httpServletRequest.getInputStream(), StandardCharsets.UTF_8);
        String username = null, password = null;
        if (StringUtils.hasText(body)) {
            JSONObject jsonObject = JSON.parseObject(body);
            username = jsonObject.getString("username");
            password = jsonObject.getString("password");
        }
        if (username == null) {username = "";}
        if (password == null) {username = "";}
        username = username.trim();
        // put info into token to pass over
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
