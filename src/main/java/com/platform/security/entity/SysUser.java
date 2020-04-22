package com.platform.security.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author code maker
 * @since 2020-04-22
 */
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 账号
     */
    private String account;
    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 上一次登录时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;
    /**
     * 账号是否可用。默认为1（可用）
     */
    private Integer enabled;
    /**
     * 是否过期。默认为1（没有过期）
     */
    @TableField("account_non_expired")
    private Integer accountNonExpired;
    /**
     * 账号是否锁定。默认为1（没有锁定）
     */
    @TableField("account_non_locked")
    private Integer accountNonLocked;
    /**
     * 证书（密码）是否过期。默认为1（没有过期）
     */
    @TableField("credentials_non_expired")
    private Integer credentialsNonExpired;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 创建人
     */
    @TableField("create_user")
    private Integer createUser;
    /**
     * 修改人
     */
    @TableField("update_user")
    private Integer updateUser;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Integer accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Integer getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Integer accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Integer getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Integer credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SysUser{" +
        ", id=" + id +
        ", account=" + account +
        ", userName=" + userName +
        ", password=" + password +
        ", lastLoginTime=" + lastLoginTime +
        ", enabled=" + enabled +
        ", accountNonExpired=" + accountNonExpired +
        ", accountNonLocked=" + accountNonLocked +
        ", credentialsNonExpired=" + credentialsNonExpired +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", createUser=" + createUser +
        ", updateUser=" + updateUser +
        "}";
    }
}
