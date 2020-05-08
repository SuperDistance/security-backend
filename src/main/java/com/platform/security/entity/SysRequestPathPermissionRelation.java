package com.platform.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 路径权限关联表
 * </p>
 *
 * @author code maker
 * @since 2020-04-23
 */
public class SysRequestPathPermissionRelation implements Serializable {


    public SysRequestPathPermissionRelation(Integer id, Integer urlId, Integer permissionId) {
        setId(id);
        setUrlId(urlId);
        setPermissionId(permissionId);
    }

    public SysRequestPathPermissionRelation(Integer urlId, Integer permissionId) {
        this.urlId = urlId;
        this.permissionId = permissionId;
    }

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 请求路径id
     */
    private Integer urlId;

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
    public String toString() {
        return "SysRequestPathPermissionRelation{" +
        "id=" + id +
        ", urlId=" + urlId +
        ", permissionId=" + permissionId +
        "}";
    }
}
