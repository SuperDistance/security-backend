/*
 * Copyright (c) 2020
 */

package com.platform.security.controller;/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 5:53 PM
 */

import com.alibaba.fastjson.JSON;
import com.platform.security.common.entity.JsonResult;
import com.platform.security.common.utils.ResultTool;
import com.platform.security.entity.SysUserRoleRelation;
import com.platform.security.service.SysUserRoleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *@program: JWTtest
 *@description: to manage the relation between role and users
 *@author: Tianshi Chen
 *@create: 2020-05-01 17:53
 */
@RestController
@EnableAutoConfiguration
public class UserRoleRelationController {
    @Autowired
    private SysUserRoleRelationService sysUserRoleRelationService;


    @GetMapping("/userRole")
    public JsonResult getUserRoleList () {
        return ResultTool.success(sysUserRoleRelationService.list());
    }

    @PostMapping("/userRoleByIds")
    public JsonResult getRoleBatch (@RequestParam String userIds){
        System.out.println("before " + userIds);
        List<Integer> list = JSON.parseArray(userIds, Integer.class);
        System.out.println("the list of id to query " + list);
        return ResultTool.success(sysUserRoleRelationService.listByIds(list));
    }

    @PostMapping("/userRole")
    public JsonResult addUserRole(@RequestParam int userId, @RequestParam int roleId) {
        return ResultTool.success(sysUserRoleRelationService.save(new SysUserRoleRelation(userId, roleId)));
    }
    @PutMapping("/userRole")
    public JsonResult updateUserRole(@RequestParam int id, @RequestParam int userId, @RequestParam int roleId) {
        return ResultTool.success(sysUserRoleRelationService.updateById(new SysUserRoleRelation(id, userId, roleId)));
    }
    @DeleteMapping("/userRole")
    public JsonResult deleteUserRole(@RequestParam int id) {
        return ResultTool.success(sysUserRoleRelationService.removeById(id));
    }
}
