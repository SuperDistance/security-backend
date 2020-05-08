/*
 * Copyright (c) 2020
 */

package com.platform.security.config.handler;
/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 12:34 PM
 */

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;

/**
 *@program: JWTtest
 *@description: support jwt login process with json
 *@author: Tianshi Chen
 *@create: 2020-04-27 12:34
 */
public class JwtLoginConfigurer<T extends  JwtLoginConfigurer<T, B>,
        B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {
    private CustomizeUsernamePasswordAuthenticationFilter authFilter;
    public JwtLoginConfigurer() {
        this.authFilter = new CustomizeUsernamePasswordAuthenticationFilter();
    }

    @Override
    public void configure(B http) throws Exception {
        // set the AuthenticationManager for Filter
        authFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        // set the failure handler
        authFilter.setAuthenticationFailureHandler(new CustomizeAuthenticationFailureHandler());
        // do not put authenticated context into session
        authFilter.setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());

        CustomizeUsernamePasswordAuthenticationFilter filter = postProcess(authFilter);
        // set the order of the customized filter
        http.addFilterAfter(filter, LogoutFilter.class);
    }
    public JwtLoginConfigurer<T, B> loginSuccessHandler (AuthenticationSuccessHandler authenticationSuccessHandler) {
        authFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        return this;
    }
}
