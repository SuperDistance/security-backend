/*
 * Copyright (c) 2020
 */

package com.platform.security.dao;

import com.platform.security.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * for sql injection Mapper 接口
 * </p>
 *
 * @author code maker
 * @since 2020-05-06
 */
public interface UserDao extends BaseMapper<User> {
    List<User> selectById (@Param("id") String id);
}
