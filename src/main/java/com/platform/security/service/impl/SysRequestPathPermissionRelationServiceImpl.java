package com.platform.security.service.impl;

import com.platform.security.entity.SysRequestPathPermissionRelation;
import com.platform.security.dao.SysRequestPathPermissionRelationDao;
import com.platform.security.service.SysRequestPathPermissionRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 路径权限关联表 服务实现类
 * </p>
 *
 * @author code maker
 * @since 2020-04-23
 */
@Service
public class SysRequestPathPermissionRelationServiceImpl extends ServiceImpl<SysRequestPathPermissionRelationDao, SysRequestPathPermissionRelation> implements SysRequestPathPermissionRelationService {

    @Resource
    private SysRequestPathPermissionRelationDao sysRequestPathPermissionRelationDao;
    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<SysRequestPathPermissionRelation> queryAllByLimit(int offset, int limit) {
        return this.sysRequestPathPermissionRelationDao.queryAllByLimit(offset, limit);
    }
}
