package com.platform.security.dao;

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

    int update(SysUser sysUser);
}
