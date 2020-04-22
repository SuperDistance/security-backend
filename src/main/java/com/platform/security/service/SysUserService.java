package com.platform.security.service;

import com.platform.security.entity.SysUser;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author code maker
 * @since 2020-04-22
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    SysUser selectByName(String userName);

    List<SysUser> queryAllByLimit(int i, int i1);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    SysUser update(SysUser sysUser);
}
