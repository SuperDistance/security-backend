package com.platform.security.service.impl;

import com.platform.security.entity.SysUser;
import com.platform.security.dao.SysUserDao;
import com.platform.security.service.SysUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author code maker
 * @since 2020-04-22
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;

    @Override
    public SysUser selectByName(String userName) {
        return this.sysUserDao.selectByName(userName);
    }

    @Override
    public List<SysUser> queryAllByLimit(int offset, int limit) {
        return this.sysUserDao.queryAllByLimit(offset, limit);
    }

    @Override
    public SysUser update(SysUser sysUser) {
        this.sysUserDao.update(sysUser);
        return this.sysUserDao.selectById(sysUser.getId());
    }
}
