package com.platform.security.dao;

import com.platform.security.entity.SysPermission;
import com.platform.security.entity.SysUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author code maker
 * @since 2020-04-22
 */
public interface SysUserDao extends BaseMapper<SysUser> {

    List<SysUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    SysUser selectByName(String userName);

    int updateUser(SysUser sysUser);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysPermission queryById(Integer id);
}
