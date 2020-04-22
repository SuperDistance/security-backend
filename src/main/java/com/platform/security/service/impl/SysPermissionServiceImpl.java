package com.platform.security.service.impl;

import com.platform.security.dao.SysPermissionDao;
import com.platform.security.entity.SysPermission;
import com.platform.security.service.SysPermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author code maker
 * @since 2020-04-22
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionDao, SysPermission> implements SysPermissionService
{
    @Resource
    private SysPermissionDao sysPermissionDao;
    @Override
    public List<SysPermission> selectListByUser(Integer userId) {
        return sysPermissionDao.selectListByUser(userId);
    }
}
