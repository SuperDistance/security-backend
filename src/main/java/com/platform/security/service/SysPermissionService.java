package com.platform.security.service;

import com.platform.security.entity.SysPermission;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author code maker
 * @since 2020-04-22
 */
public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysPermission queryById(Integer id);

    /**
     * 查询用户的权限列表
     *
     * @param userId
     * @return
     */
    List<SysPermission> selectListByUser(Integer userId);

    /**
     * 查询具体某个接口的权限
     *
     * @param path
     * @return
     */
    List<SysPermission> selectListByPath(String path);
}
