package com.platform.security.dao;

import com.platform.security.entity.SysRequestPath;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 请求路径 Mapper 接口
 * </p>
 *
 * @author code maker
 * @since 2020-04-22
 */
public interface SysRequestPathDao extends BaseMapper<SysRequestPath> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysRequestPath queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysRequestPath> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);
}
