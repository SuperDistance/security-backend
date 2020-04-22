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
}
