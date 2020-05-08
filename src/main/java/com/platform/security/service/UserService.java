/*
 * Copyright (c) 2020
 */

package com.platform.security.service;

import com.platform.security.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * for sql injection 服务类
 * </p>
 *
 * @author code maker
 * @since 2020-05-06
 */
public interface UserService extends IService<User> {
    List<User> selectById (String id);
}
