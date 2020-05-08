package com.platform.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 用户角色关联关系表
 * </p>
 *
 * @author code maker
 * @since 2020-04-23
 */
public class SysUserRoleRelation implements Serializable {

    public SysUserRoleRelation(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public SysUserRoleRelation(Integer id, Integer userId, Integer roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SysUserRoleRelation{" +
        "id=" + id +
        ", userId=" + userId +
        ", roleId=" + roleId +
        "}";
    }
}
