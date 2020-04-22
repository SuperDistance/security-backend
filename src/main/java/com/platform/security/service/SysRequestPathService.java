package com.platform.security.service;

import com.platform.security.entity.SysPermission;
import com.platform.security.entity.SysRequestPath;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 请求路径 服务类
 * </p>
 *
 * @author code maker
 * @since 2020-04-22
 */
public interface SysRequestPathService extends IService<SysRequestPath> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysRequestPath queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysRequestPath> queryAllByLimit(int offset, int limit);

}
