package com.platform.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 角色-权限关联关系表
 * </p>
 *
 * @author code maker
 * @since 2020-04-23
 */
public class SysRolePermissionRelation implements Serializable {

    public SysRolePermissionRelation(Integer id, Integer roleId, Integer permissionId) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public SysRolePermissionRelation(Integer roleId, Integer permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 权限id
     */
    private Integer permissionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "SysRolePermissionRelation{" +
        "id=" + id +
        ", roleId=" + roleId +
        ", permissionId=" + permissionId +
        "}";
    }
}
