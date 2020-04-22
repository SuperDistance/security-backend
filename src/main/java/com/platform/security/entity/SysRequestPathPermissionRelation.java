package com.platform.security.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 路径权限关联表
 * </p>
 *
 * @author code maker
 * @since 2020-04-22
 */
@TableName("sys_request_path_permission_relation")
public class SysRequestPathPermissionRelation extends Model<SysRequestPathPermissionRelation> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;
    /**
     * 请求路径id
     */
    @TableField("url_id")
    private Integer urlId;
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

    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
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
        return "SysRequestPathPermissionRelation{" +
        ", id=" + id +
        ", urlId=" + urlId +
        ", permissionId=" + permissionId +
        "}";
    }
}
