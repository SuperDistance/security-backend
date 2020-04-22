/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 2020/4/22
 */
package com.platform.security.config;

import com.platform.security.config.handler.CustomizeAuthenticationEntrypoint;
import com.platform.security.config.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *@program: security-backend
 *@description: Spring Security Core Config
 *@author: Tianshi Chen
 *@create: 2020-04-22 15:25
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
    *@Description: Add JDBC user authentication method
    *@Param: []
    *@return: org.springframework.security.core.userdetails.UserDetailsService
    *@Author: Tianshi Chen
    *@date: 4/22/2020
    */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Autowired
    CustomizeAuthenticationEntrypoint customizeAuthenticationEntrypoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // configuration methods
        super.configure(auth);
        auth.userDetailsService(userDetailsService());
    }
    /**
    *@Description: http-related configuration including login, log out, exception dealing, session management
    *@Param: [httpSecurity]
    *@return: void
    *@Author: Tianshi Chen
    *@date: 4/22/2020
    */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().
                antMatchers("/getUser").hasAuthority("query_user").

                //异常处理(权限拒绝、登录失效等)
                and().exceptionHandling().
                authenticationEntryPoint(customizeAuthenticationEntrypoint);// 匿名用户访问无权限资源时的异常处理



    }
    /**
    *@Description: offer an default encrypt method
    *@Param: []
    *@return: org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
    *@Author: Tianshi Chen
    *@date: 4/22/2020
    */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
