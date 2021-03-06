package com.platform.security.service.impl;

import com.platform.security.entity.SysRequestPath;
import com.platform.security.dao.SysRequestPathDao;
import com.platform.security.service.SysRequestPathService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 请求路径 服务实现类
 * </p>
 *
 * @author code maker
 * @since 2020-04-23
 */
@Service
public class SysRequestPathServiceImpl extends ServiceImpl<SysRequestPathDao, SysRequestPath> implements SysRequestPathService {
    @Resource
    private SysRequestPathDao sysRequestPathDao;

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysRequestPath> queryAllByLimit(int offset, int limit) {
        return this.sysRequestPathDao.queryAllByLimit(offset, limit);
    }
}
