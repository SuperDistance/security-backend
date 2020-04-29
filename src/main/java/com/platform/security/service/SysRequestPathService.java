package com.platform.security.service;

import com.platform.security.entity.SysRequestPath;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 请求路径 服务类
 * </p>
 *
 * @author code maker
 * @since 2020-04-23
 */
public interface SysRequestPathService extends IService<SysRequestPath> {
    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysRequestPath> queryAllByLimit(int offset, int limit);

}
