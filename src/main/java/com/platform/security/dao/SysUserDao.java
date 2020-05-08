/*
 * Copyright (c) 2020
 */

package com.platform.security.dao;

import com.platform.security.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author code maker
 * @since 2020-04-23
 */
public interface SysUserDao extends BaseMapper<SysUser> {

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    SysUser selectByName(@Param("userName") String userName);
}
