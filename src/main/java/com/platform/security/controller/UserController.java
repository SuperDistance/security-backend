/*
 * Copyright (c) 2020
 */

package com.platform.security.controller;/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 6:53 PM
 */

import com.platform.security.common.entity.JsonResult;
import com.platform.security.common.utils.ResultTool;
import com.platform.security.entity.SysUser;
import com.platform.security.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *@program: security-backend
 *@description: a test
 *@author: Tianshi Chen
 *@create: 2020-04-22 18:53
 */
@RestController
public class UserController {
    @Autowired
    SysUserService sysUserService;

    @GetMapping("/getUser")
    public JsonResult getUser() {
        List<SysUser> users = sysUserService.queryAllByLimit(1, 100);
        return ResultTool.success(users);
    }
    @GetMapping("/test")
    public JsonResult test() {
        return ResultTool.success("hello world");
    }
}
