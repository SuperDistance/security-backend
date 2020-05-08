/*
 * Copyright (c) 2020
 */

package com.platform.security.config;
/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 7:49 PM
 */

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *@program: JWTtest
 *@description: to enable page query
 *@author: Tianshi Chen
 *@create: 2020-05-01 19:49
 */
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {

    // 分页插件
    // https://mp.baomidou.com/guide/page.html
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
}
