package com.platform.security.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 角色-权限关联关系表
 * </p>
 *
 * @author code maker
 * @since 2020-04-22
 */
@TableName("sys_role_permission_relation")
public class SysRolePermissionRelation extends Model<SysRolePermissionRelation> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 角色id
     */
    @TableField("role_id")
    private Integer roleId;
    /**
     * 权限id
     */
    @TableField("permission_id")
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
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysRolePermissionRelation{" +
        ", id=" + id +
        ", roleId=" + roleId +
        ", permissionId=" + permissionId +
        "}";
    }
}
