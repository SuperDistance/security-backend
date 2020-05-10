/*
 * Copyright (c) 2020
 */

package com.platform.security.config.handler;
/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 6:03 PM
 */

import com.alibaba.fastjson.JSON;
import com.platform.security.common.entity.JsonResult;
import com.platform.security.common.enums.ResultCode;
import com.platform.security.common.utils.JwtTokenUtil;
import com.platform.security.common.utils.ResultTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *@program: JWTtest
 *@description: Jwt Filter
 *@author: Tianshi Chen
 *@create: 2020-04-26 18:03
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        AuthenticationException failed = null;

            String token = httpServletRequest.getHeader(jwtTokenUtil.getHeader());
            String username = null;
            if (!StringUtils.isEmpty(token)) {
                username = jwtTokenUtil.getUsernameFromToken(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    System.out.println("now the userDetails is" + userDetails);
                    String userName = userDetails.getUsername();
                    if (userName == null) {
                        throw new UsernameNotFoundException("User: " + username + " not exist!");
                    }
                    if (jwtTokenUtil.validateToken(token, userDetails)) {
                        // refresh
                        boolean shouldRefresh = shouldTokenRefresh(jwtTokenUtil.issuedAt(token));
                        if(shouldRefresh) {
                            System.out.println("shouldRefresh: " + shouldRefresh);
                            token = jwtTokenUtil.refreshToken(token);
                            httpServletResponse.setHeader(jwtTokenUtil.getHeader(), token);
                        }
                        // put the userDetails into authentication，to use later
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        // save authentication into ThreadLocal，to use later
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                    else {
                        JsonResult jsonResult = ResultTool.fail(ResultCode.JWT_EXPIRED);
                        httpServletResponse.setContentType("text/json;charset=utf-8");
                        httpServletResponse.getWriter().write(JSON.toJSONString(jsonResult));
                        throw new BadCredentialsException ("validate failed");
                    }
                }
                else {
                    JsonResult jsonResult = ResultTool.fail(ResultCode.JWT_ERROR);
                    httpServletResponse.setContentType("text/json;charset=utf-8");
                    httpServletResponse.getWriter().write(JSON.toJSONString(jsonResult));
                    throw new InternalAuthenticationServiceException("JWT ERROR!");
                }
            }
            else {  //如果token长度为0
                JsonResult jsonResult = ResultTool.fail(ResultCode.JWT_EMPTY);
                httpServletResponse.setContentType("text/json;charset=utf-8");
                httpServletResponse.getWriter().write(JSON.toJSONString(jsonResult));
                throw new InsufficientAuthenticationException("JWT is Empty");
            }
            System.out.println("now the token is " + token);
            System.out.println("now the username is " + username);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean shouldTokenRefresh(Date issueAt){
        LocalDateTime issueTime = LocalDateTime.ofInstant(issueAt.toInstant(), ZoneId.systemDefault());
        System.out.println("Interval to refresh token: " + LocalDateTime.now().plusSeconds(JwtTokenUtil.getTokenRefreshInterval()));
        return LocalDateTime.now().plusSeconds(JwtTokenUtil.getTokenRefreshInterval()).isAfter(issueTime);
    }
}
