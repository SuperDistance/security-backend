/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 4:21 PM
 */

package com.platform.security.common.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @program: security-backend
 * @description: Mybatis Plus code generator
 * @author: Tianshi Chen
 * @create: 2020-04-22 16:21
 */

/**
 * mybatis代码生成工具
 * 官网：http://mp.baomidou.com
 * @Author wly
 * @Date 2018/7/4 14:10
 */
public class MybatisPlusGenerate {
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // =============================全局配置===============================
        mpg.setGlobalConfig(new GlobalConfig()
                // 覆盖输出到xxx目录
                .setFileOverride(false).setOutputDir("C:\\Users\\92351\\Documents\\GitHub\\security-backend\\MPcode")
                // 主键生成策略 生成BaseResultMap
                .setIdType(IdType.AUTO).setBaseResultMap(true)
                // 指定作者
                .setAuthor("code maker")
                // 设置Controller、Service、ServiceImpl、Dao、Mapper文件名称，%s是依据表名转换来的
                .setControllerName("%sController").setServiceName("%sService").setServiceImplName("%sServiceImpl").setMapperName("%sDao").setXmlName("%sMapper"));
        // ================================数据源配置============================
        mpg.setDataSource(new DataSourceConfig()
                // 用户名、密码、驱动、url
                .setDbType(DbType.MYSQL).setDriverName("com.mysql.jdbc.Driver")
                .setDriverName("com.mysql.jdbc.Driver")
                .setUrl("jdbc:mysql://rm-2ze302nbs3k113x8klo.mysql.rds.aliyuncs.com:3306/security?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=CTT")
                .setUsername("dbadmin")
                .setPassword("Zker1678$&")
        );
        // ===============================包名配置：父包.模块.controller===============================
        mpg.setPackageInfo(new PackageConfig()
                // 父包名 模块名
                .setParent("com.platform").setModuleName("security")
                // 分层包名
                .setController("controller").setService("service").setServiceImpl("service.impl").setEntity("entity").setMapper("dao")
                .setXml("mapper"));
        // =====================================策略配置==================================
        mpg.setStrategy(new StrategyConfig()
                // 命名策略：实体的类名和属性名按下划线转驼峰 user_info -> userInfo
                .setNaming(NamingStrategy.underline_to_camel)
                // controller生成@RestCcontroller
                .setRestControllerStyle(true));
        // 执行生成
        mpg.execute();

    }
}

