package com.platform.security.service.impl;

import com.platform.security.entity.SysUser;
import com.platform.security.dao.SysUserDao;
import com.platform.security.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author code maker
 * @since 2020-04-23
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

}
