package com.platform.security.service.impl;

import com.platform.security.entity.SysPermission;
import com.platform.security.dao.SysPermissionDao;
import com.platform.security.service.SysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author code maker
 * @since 2020-04-23
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionDao, SysPermission> implements SysPermissionService {
    @Resource
    private SysPermissionDao sysPermissionDao;
    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysPermission> queryAllByLimit(int offset, int limit) {
        return this.sysPermissionDao.queryAllByLimit(offset, limit);
    }
    @Override
    public List<SysPermission> selectListByUser(Integer userId) {
        return sysPermissionDao.selectListByUser(userId);
    }

    @Override
    public List<SysPermission> selectListByPath(String path) {
        return sysPermissionDao.selectListByPath(path);
    }
}
