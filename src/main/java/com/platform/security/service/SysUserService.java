package com.platform.security.service;

import com.platform.security.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author code maker
 * @since 2020-04-23
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysUser> queryAllByLimit(int offset, int limit);

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    SysUser selectByName(String userName);

    /**
    *@Description: register user able to return error code
    *@Param: [sysUser]
    *@return: java.lang.int
    *@Author: Tianshi Chen
    *@date: 5/1/2020
    */
    int registerOrUpdateUser(SysUser sysUser, boolean isUpdate, boolean revisePassword, String newPassword, boolean isAdminister);
}
