package com.platform.security.dao;

import com.platform.security.entity.SysPermission;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author code maker
 * @since 2020-04-22
 */
public interface SysPermissionDao extends BaseMapper<SysPermission> {
    /**
     * 查询用户的权限
     *
     * @param userId
     * @return
     */
    List<SysPermission> selectListByUser(Integer userId);

    /**
     * 查询具体某个接口的权限
     *
     * @param path 接口路径
     * @return
     */
    List<SysPermission> selectListByPath(String path);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysPermission queryById(Integer id);
}
