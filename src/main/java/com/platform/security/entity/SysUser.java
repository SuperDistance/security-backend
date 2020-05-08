/*
 * Copyright (c) 2020
 */

package com.platform.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author code maker
 * @since 2020-04-23
 */
@Data
public class SysUser implements Serializable {


    public SysUser(String account, String userName, String rawPassword) {
        setAccount(account);
        setUserName(userName);
        setPassword(rawPassword);
        setCreateTime(LocalDateTime.now());
        setUpdateTime(LocalDateTime.now());
    }

    public SysUser(String account, String userName, String rawPassword, List<String> status) {
        setAccount(account);
        setUserName(userName);
        setPassword(rawPassword);
        setCreateTime(LocalDateTime.now());
        setUpdateTime(LocalDateTime.now());
        setAccountNonExpired(false);
        setCredentialsNonExpired(false);
        setAccountNonLocked(false);
        setEnabled(false);
        for (String s: status) {
            if (s.equals("accountNonExpired")) setAccountNonExpired(true);
            if (s.equals("accountNonLocked")) setAccountNonLocked(true);
            if (s.equals("credentialsNonExpired")) setCredentialsNonExpired(true);
            if (s.equals("enabled")) setEnabled(true);
        }
    }

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 上一次登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 账号是否可用。默认为1（可用）
     */
    private Boolean enabled;

    /**
     * 是否过期。默认为1（没有过期）
     */
    private Boolean accountNonExpired;

    /**
     * 账号是否锁定。默认为1（没有锁定）
     */
    private Boolean accountNonLocked;

    /**
     * 证书（密码）是否过期。默认为1（没有过期）
     */
    private Boolean credentialsNonExpired;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 修改人
     */
    private Integer updateUser;

    public SysUser(Integer id, String account, String userName, String password, LocalDateTime lastLoginTime, Boolean enabled, Boolean accountNonExpired, Boolean accountNonLocked, Boolean credentialsNonExpired, LocalDateTime createTime, LocalDateTime updateTime, Integer createUser, Integer updateUser) {
        this.id = id;
        this.account = account;
        this.userName = userName;
        this.password = password;
        this.lastLoginTime = lastLoginTime;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createUser = createUser;
        this.updateUser = updateUser;
    }


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

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
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
    public String toString() {
        return "SysUser{" +
        "id=" + id +
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
