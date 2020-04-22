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
     * 查询用户的权限列表
     *
     * @param userId
     * @return
     */
    List<SysPermission> selectListByUser(Integer userId);

}
