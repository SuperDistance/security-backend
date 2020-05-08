/*
 * Copyright (c) 2020
 */

package com.platform.security.controller;/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 4:51 PM
 */

import com.platform.security.common.entity.JsonResult;
import com.platform.security.common.utils.ResultTool;
import com.platform.security.entity.SysRole;
import com.platform.security.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

/**
 *@program: JWTtest
 *@description: to manage the roles
 *@author: Tianshi Chen
 *@create: 2020-05-01 16:51
 */
@RestController
@EnableAutoConfiguration
public class RoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @GetMapping("/role")
    public JsonResult getRoleList () {
        return ResultTool.success(sysRoleService.list());
    }
    @PostMapping("/role")
    public JsonResult addRole(@RequestParam String roleName, @RequestParam String roleDescription, @RequestParam String roleCode) {
        return ResultTool.success(sysRoleService.save(new SysRole(roleName, roleDescription, roleCode)));
    }
    @PutMapping("/role")
    public JsonResult updateRole(@RequestParam int id, @RequestParam String roleName, @RequestParam String roleDescription, @RequestParam String roleCode) {
        return ResultTool.success(sysRoleService.updateById(new SysRole(id, roleName, roleDescription, roleCode)));
    }
    @DeleteMapping("/role")
    public JsonResult deleteRole(@RequestParam int id) {
        return ResultTool.success(sysRoleService.removeById(id));
    }
}
