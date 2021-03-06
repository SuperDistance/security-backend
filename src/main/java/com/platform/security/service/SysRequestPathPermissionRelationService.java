package com.platform.security.service;

import com.platform.security.entity.SysRequestPathPermissionRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 路径权限关联表 服务类
 * </p>
 *
 * @author code maker
 * @since 2020-04-23
 */
public interface SysRequestPathPermissionRelationService extends IService<SysRequestPathPermissionRelation> {
    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysRequestPathPermissionRelation> queryAllByLimit(int offset, int limit);
}
