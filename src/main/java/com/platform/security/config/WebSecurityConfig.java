/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 2020/4/22
 */
package com.platform.security.config;

import com.platform.security.config.handler.*;
import com.platform.security.config.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

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

    @Autowired
    CustomizeAuthenticationEntryPoint customizeAuthenticationEntrypoint;

    @Autowired
    CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;

    @Autowired
    CustomizeAuthenticationFailureHandler customizeAuthenticationFailureHandler;

    @Autowired
    CustomizeLogoutSuccessHandler customizeLogoutSuccessHandler;

    @Autowired
    CustomizeSessionInformationExpiredStrategy customizeSessionInformationExpiredStrategy;

    @Autowired
    CustomizeAccessDecisionManager customizeAccessDecisionManager;

    @Autowired
    CustomizeFilterInvocationSecurityMetadataSource customizeFilterInvocationSecurityMetadataSource;

    @Autowired
    private CustomizeAbstractSecurityInterceptor customizeAbstractSecurityInterceptor;

    // offer an default encrypt method
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

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
        httpSecurity.cors().and().csrf().disable();
        httpSecurity.authorizeRequests().
                withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        // access decision manager added
                        o.setAccessDecisionManager(customizeAccessDecisionManager);
                        // security metadata source added: provide the list of url is valid
                        o.setSecurityMetadataSource(customizeFilterInvocationSecurityMetadataSource);
                        return o;
                    }
                }).

                // exceptions dealing(eg. login refused, invalid)
                and().exceptionHandling().
                authenticationEntryPoint(customizeAuthenticationEntrypoint). // to deal with anonymous user's access to resources

                // log in
                and().formLogin().
                    permitAll().
                    successHandler(customizeAuthenticationSuccessHandler).
                    failureHandler(customizeAuthenticationFailureHandler).

                // log out
                and().logout().
                    permitAll().
                    logoutSuccessHandler(customizeLogoutSuccessHandler).
                    deleteCookies("JSESSIONID").

                // session policy
                and().sessionManagement().
                        maximumSessions(1).
                        expiredSessionStrategy(customizeSessionInformationExpiredStrategy);

                httpSecurity.addFilterBefore(customizeAbstractSecurityInterceptor, FilterSecurityInterceptor.class);
    }


}
