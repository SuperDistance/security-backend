/*
 * Copyright (c) 2020
 */

package com.platform.security.controller;/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 5:04 PM
 */

import com.platform.security.common.entity.JsonResult;
import com.platform.security.common.utils.ResultTool;
import com.platform.security.entity.SysPermission;
import com.platform.security.entity.SysRole;
import com.platform.security.service.SysPermissionService;
import com.platform.security.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

/**
 *@program: JWTtest
 *@description: to manage the permission
 *@author: Tianshi Chen
 *@create: 2020-05-01 17:04
 */
@RestController
@EnableAutoConfiguration
public class PermissionController {
    @Autowired
    private SysPermissionService sysPermissionService;
    @GetMapping("/permission")
    public JsonResult getPermissionList () {
        return ResultTool.success(sysPermissionService.list());
    }
    @PostMapping("/permission")
    public JsonResult addPermission(@RequestParam String permissionCode, @RequestParam String permissionName) {
        return ResultTool.success(sysPermissionService.save(new SysPermission(permissionCode, permissionName)));
    }
    @PutMapping("/permission")
    public JsonResult updatePermission(@RequestParam int id, @RequestParam String permissionCode, @RequestParam String permissionName) {
        return ResultTool.success(sysPermissionService.updateById(new SysPermission(id, permissionCode, permissionName)));
    }
    @DeleteMapping("/permission")
    public JsonResult deletePermission(@RequestParam int id) {
        return ResultTool.success(sysPermissionService.removeById(id));
    }
}
