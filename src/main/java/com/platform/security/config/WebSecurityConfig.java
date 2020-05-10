/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 2020/4/22
 */
package com.platform.security.config;

import com.platform.security.config.access.CustomizeAbstractSecurityInterceptor;
import com.platform.security.config.access.CustomizeAccessDecisionManager;
import com.platform.security.config.access.CustomizeAccessDeniedHandler;
import com.platform.security.config.access.CustomizeFilterInvocationSecurityMetadataSource;
import com.platform.security.config.jwt.*;
import com.platform.security.config.login.CustomizeAuthenticationFailureHandler;
import com.platform.security.config.login.CustomizeAuthenticationSuccessHandler;
import com.platform.security.config.login.CustomizeLogoutSuccessHandler;
import com.platform.security.config.login.JwtLoginConfigurer;
import com.platform.security.config.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

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
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    CustomizeAuthenticationEntryPoint customizeAuthenticationEntrypoint;

    @Autowired
    CustomizeLogoutSuccessHandler customizeLogoutSuccessHandler;

    @Autowired
    CustomizeAccessDecisionManager customizeAccessDecisionManager;

    @Autowired
    CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;

    @Autowired
    CustomizeAuthenticationFailureHandler customizeAuthenticationFailureHandler;

    @Autowired
    CustomizeFilterInvocationSecurityMetadataSource customizeFilterInvocationSecurityMetadataSource;

    @Autowired
    CustomizeAccessDeniedHandler customizeAccessDeniedHandler;

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
        auth.authenticationProvider(daoAuthenticationProvider());
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
        // 在 UsernamePasswordAuthenticationFilter 之前添加 JwtAuthenticationTokenFilter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // JWT (eg. login refused, invalid)

        httpSecurity.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().formLogin().disable()
                .headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(
                        new Header("Access-control-Allow-Origin","*"),
                new Header("Access-Control-Expose-Headers","Authorization"))));

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

                // log in
                and().apply(new JwtLoginConfigurer<>()).loginSuccessHandler(customizeAuthenticationSuccessHandler).

                // log out
                and().logout().
                    permitAll().
                    logoutSuccessHandler(customizeLogoutSuccessHandler).
//                    deleteCookies("JSESSIONID").

                and().headers().cacheControl();


        httpSecurity.addFilterBefore(customizeAbstractSecurityInterceptor, FilterSecurityInterceptor.class);
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();
        //让Spring security 放行所有preflight request（cors 预检请求）
        registry.requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
        // 处理异常情况：认证失败和权限不足
        // exceptions dealing(eg. login refused, invalid)
        // to deal with anonymous user's access to resources
        httpSecurity.exceptionHandling().
                authenticationEntryPoint(customizeAuthenticationEntrypoint).
                accessDeniedHandler(customizeAccessDeniedHandler);
    }


    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean("daoAuthenticationProvider")
    protected AuthenticationProvider daoAuthenticationProvider() throws Exception{
        //这里使用BCryptPasswordEncoder比对加密后的密码，注意要跟createUser时保持一致
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setPasswordEncoder(passwordEncoder());
        daoProvider.setUserDetailsService(userDetailsService());
        return daoProvider;
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowCredentials(true);
        cors.addAllowedOrigin("*");
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        configurationSource.registerCorsConfiguration("/**", cors);
        return new CorsFilter(configurationSource);
    }
}
