/*
 * Copyright (c) 2020
 */

package com.platform.security.controller;/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 6:53 PM
 */

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.security.common.entity.JsonResult;
import com.platform.security.common.enums.ResultCode;
import com.platform.security.common.utils.ResultTool;
import com.platform.security.common.utils.RoleUtil;
import com.platform.security.common.utils.UserUtil;
import com.platform.security.entity.SysRole;
import com.platform.security.entity.SysUser;
import com.platform.security.entity.SysUserRoleRelation;
import com.platform.security.service.SysRoleService;
import com.platform.security.service.SysUserRoleRelationService;
import com.platform.security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@program: security-backend
 *@description: a test
 *@author: Tianshi Chen
 *@create: 2020-04-22 18:53
 */
@RestController
@EnableAutoConfiguration
public class UserController {
    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysUserRoleRelationService sysUserRoleRelationService;

    @GetMapping("/getUser")
    public JsonResult getUser(@RequestParam int pageNo, @RequestParam int pageSize) {
        IPage<SysUser> page = new Page<>(pageNo, pageSize);
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        Object users = sysUserService.page(page, wrapper);
        System.out.println("getUsers: " + users);
        return ResultTool.success(users);
    }
    @GetMapping("/getUser/number")
    public JsonResult getUserCounts() {
        return ResultTool.success(sysUserService.count());
    }

    // regular register; restrict the role to be normalUser
    @PostMapping("/user/add")
    public JsonResult registerUser(@RequestParam(required = false) String createUserId, @RequestParam String account, @RequestParam String username, @RequestParam String password) {
        SysUser sysUser = new SysUser(account, username, password);
        if (createUserId != null && !createUserId.isEmpty()) {
            sysUser.setCreateUser(Integer.parseInt(createUserId));
        }
        int resultCode = sysUserService.registerOrUpdateUser(sysUser, false, false, null, true);
        if (resultCode == 0) {
            // get the user id currently created (id is auto increment)
            SysUser temp = sysUserService.getOne(new QueryWrapper<SysUser>().eq("account", sysUser.getAccount()));
            // restrict the role to be normalUser
            sysUserRoleRelationService.save(new SysUserRoleRelation(temp.getId(), RoleUtil.getNormalUserId()));
        }
        return getRegisterErrorMessage(resultCode);
    }

    // top-level register
    @PostMapping("/addUser")
    public JsonResult registerUser(@RequestParam String role, @RequestParam(required = false) String createUserId, @RequestParam String account, @RequestParam String username, @RequestParam String password) {
        SysUser sysUser = new SysUser(account, username, password);
        if (createUserId != null && !createUserId.isEmpty()) {
            sysUser.setCreateUser(Integer.parseInt(createUserId));
        }  else {
            // get the creat user from context and fetch the id to use as creat user
            int id = sysUserService.getOne(new QueryWrapper<SysUser>().eq("account", UserUtil.getCurrentUserAccount())).getId();
            sysUser.setCreateUser(id);
        }
        int resultCode = sysUserService.registerOrUpdateUser(sysUser, false, false, null, true);
        if (resultCode == 0) {
            // get the user id currently created (id is auto increment)
            SysUser temp = sysUserService.getOne(new QueryWrapper<SysUser>().eq("account", sysUser.getAccount()));
            // get the role to creat
            QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_code", role);
            int roleId = sysRoleService.getOne(queryWrapper).getId();
            sysUserRoleRelationService.save(new SysUserRoleRelation(temp.getId(), roleId));
        }
        return getRegisterErrorMessage(resultCode);
    }

    // top-level update User
    @PutMapping("/updateUser")
    public JsonResult updateUser(@RequestParam String account, @RequestParam String username, @RequestParam String password,
                                 @RequestParam(required = false) String newPassword,  @RequestParam String status) {
        System.out.println("before " + status);
        List<String> list = JSON.parseArray(status, String.class);
        System.out.println("the list of id to query " + list);
        SysUser sysUser = new SysUser(account, username, password, list);
        boolean toRevise = newPassword != null && newPassword.equals(password) && !newPassword.isEmpty();
        System.out.println("newPassowrd: " + newPassword + "\npassword: " + password + "\ntoRevise: " + toRevise);
        int resultCode = sysUserService.registerOrUpdateUser(sysUser, true, toRevise, newPassword, true);
        return getUpdateErrorMessage(resultCode);
    }

    // regular update info
    @PutMapping("/user/update")
    public JsonResult updateSelf(@RequestParam(required = false) String updateUserId, @RequestParam String account, @RequestParam String username, @RequestParam String password,
                                 @RequestParam(required = false) String newPassword) {
        SysUser sysUser = new SysUser(account, username, password);
        if (updateUserId != null && !updateUserId.isEmpty()) {
            sysUser.setUpdateUser(Integer.parseInt(updateUserId));
        } else {
            // get the creat user from context and fetch the id to use as update user
            sysUser.setUpdateUser(sysUserService.getOne(new QueryWrapper<SysUser>().eq("account", UserUtil.getCurrentUserAccount())).getId());
        }
        int resultCode = sysUserService.registerOrUpdateUser(sysUser, true, newPassword == null, newPassword, false);
        return getUpdateErrorMessage(resultCode);
    }

    // top-level delete User
    @DeleteMapping("/deleteUser")
    public JsonResult deleteUser(@RequestParam int userId) {
        boolean result = sysUserService.removeById(userId);
        boolean result2 = sysUserRoleRelationService.remove(new QueryWrapper<SysUserRoleRelation>().eq("user_id", userId));
        if (result && result2) {
            return ResultTool.success("Success Delete!");
        } else {
            return ResultTool.fail(ResultCode.DELETE_ERROR);
        }
    }

    @GetMapping("/test")
    public JsonResult test() {
        List<SysUser> users = sysUserService.list();
        return ResultTool.success(users);
    }

    private static JsonResult getRegisterErrorMessage(int resultCode) {
        JsonResult result = null;
        switch (resultCode) {
            case 0:
            {
                result = ResultTool.success("Register User Success!");
                break;
            }
            case 1:
            {
                result = ResultTool.fail(ResultCode.USER_ACCOUNT_ALREADY_EXIST);
                break;
            }
            case 2:
            {
                result = ResultTool.fail(ResultCode.SAVE_ERROR);
                break;
            }
        }
        return result;
    }

    private static JsonResult getUpdateErrorMessage(int resultCode) {
        JsonResult result = null;
        switch (resultCode) {
            case 0:
            {
                result = ResultTool.success("Update User Success!");
                break;
            }
            case 4:
            {
                result = ResultTool.fail(ResultCode.USER_CREDENTIALS_ERROR);
                break;
            }
            case 5:
            {
                result = ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST);
                break;
            }
            case 7:
            {
                result = ResultTool.fail(ResultCode.NO_PERMISSION);
            }
        }
        return result;
    }
}
