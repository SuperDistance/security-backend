/*
 * Copyright (c) 2020
 */

package com.platform.security.config.jwt;
/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 6:03 PM
 */

import com.platform.security.common.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    // enforce to let entryPoint deal with jwt error
    @Autowired
    private CustomizeAuthenticationEntryPoint customizeAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

            String token = httpServletRequest.getHeader(jwtTokenUtil.getHeader());
            String username = null;
            try {
                    // throw UnsupportedJwtException, MalformedJwtException, SignatureException, ExpiredJwtException, IllegalArgumentException
                    username = jwtTokenUtil.getUsernameFromToken(token);
                    // make sure that now is at authentication first filter chain
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        System.out.println("now the userDetails is" + userDetails);
                        if (jwtTokenUtil.validateToken(token, userDetails)) {
                            // refresh
                            boolean shouldRefresh = jwtTokenUtil.shouldTokenRefresh(jwtTokenUtil.issuedAt(token));
                            if (shouldRefresh) {
                                System.out.println("shouldRefresh: " + true);
                                token = jwtTokenUtil.refreshToken(token);
                                // set new token into the response header
                                httpServletResponse.setHeader(jwtTokenUtil.getHeader(), token);
                            }
                            // put the userDetails into authentication，to use later
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                            // save authentication into ThreadLocal，to use later
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
            } catch (UsernameNotFoundException e) {
                customizeAuthenticationEntryPoint.commence(httpServletRequest, httpServletResponse, new UsernameNotFoundException("User: " + username + " not exist!"));
                return;
            }
            catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                // if the length of token is 0
            }
            catch (ExpiredJwtException e) {
                customizeAuthenticationEntryPoint.commence(httpServletRequest, httpServletResponse, new NonceExpiredException("JWT Expired"));
                return;
            } catch (SignatureException e) {
                customizeAuthenticationEntryPoint.commence(httpServletRequest, httpServletResponse, new BadCredentialsException("validate failed"));
                return;
            } catch (UnsupportedJwtException | MalformedJwtException e) {
                customizeAuthenticationEntryPoint.commence(httpServletRequest, httpServletResponse, new AuthenticationServiceException("JWT ERROR!"));
                return;
            }
            System.out.println("now the token is " + token);
            System.out.println("now the username is " + username);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
