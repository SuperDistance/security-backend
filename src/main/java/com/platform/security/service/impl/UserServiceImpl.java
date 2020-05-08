/*
 * Copyright (c) 2020
 */

package com.platform.security.service.impl;

import com.platform.security.entity.User;
import com.platform.security.dao.UserDao;
import com.platform.security.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * for sql injection 服务实现类
 * </p>
 *
 * @author code maker
 * @since 2020-05-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public List<User> selectById(String id) {
        return userDao.selectById(id);
    }
}
