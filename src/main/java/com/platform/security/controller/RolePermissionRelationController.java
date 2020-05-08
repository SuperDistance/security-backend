/*
 * Copyright (c) 2020
 */

package com.platform.security.controller;/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 5:43 PM
 */

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.platform.security.common.entity.JsonResult;
import com.platform.security.common.utils.ResultTool;
import com.platform.security.entity.SysRolePermissionRelation;
import com.platform.security.service.SysRolePermissionRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *@program: JWTtest
 *@description: to manage  relations between role and permissions
 *@author: Tianshi Chen
 *@create: 2020-05-01 17:43
 */
@RestController
@EnableAutoConfiguration
public class RolePermissionRelationController {
    @Autowired
    private SysRolePermissionRelationService sysRolePermissionRelationService;
    @GetMapping("/rolePermission")
    public JsonResult getRolePermissionRelationList () {
        return ResultTool.success(sysRolePermissionRelationService.list());
    }
    @PostMapping("/rolePermissionByRoleIds")
    public JsonResult getRoleBatch (@RequestParam String roleIds){
        System.out.println("before " + roleIds);
        List<Integer> list = JSON.parseArray(roleIds, Integer.class);
        QueryWrapper<SysRolePermissionRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id", list);
        // System.out.println("the list of roleId to query " + list);
        System.out.println(sysRolePermissionRelationService.list(queryWrapper));
        return ResultTool.success(sysRolePermissionRelationService.list(queryWrapper));
    }

    @PostMapping("/rolePermission")
    public JsonResult addRolePermissionRelation(@RequestParam int roleId, @RequestParam int permissionId) {
        return ResultTool.success(sysRolePermissionRelationService.save(new SysRolePermissionRelation(roleId, permissionId)));
    }

    @PostMapping("/rolePermissionByPermissionIds")
    public JsonResult updateRolePermissionRelation(@RequestParam int roleId, @RequestParam String permissionIds) {
        System.out.println("before " + permissionIds);
        List<Integer> list = JSON.parseArray(permissionIds, Integer.class);
        QueryWrapper<SysRolePermissionRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        // first to query the original permissions
       List<SysRolePermissionRelation> originalPermissions = sysRolePermissionRelationService.list(queryWrapper);
        System.out.println("original: " + originalPermissions);
        // construct all the new relations
        List<SysRolePermissionRelation> toSave = new ArrayList<>();
        for (Integer item: list) {
            toSave.add(new SysRolePermissionRelation(roleId, item));
        }
        System.out.println("original: " + toSave);
       // remove all the permission with this role
        if ((sysRolePermissionRelationService.count(queryWrapper) == 0 || sysRolePermissionRelationService.remove(queryWrapper)) && sysRolePermissionRelationService.saveBatch(toSave)) {
            return ResultTool.success();
        }
        else {
            sysRolePermissionRelationService.remove(queryWrapper);
            sysRolePermissionRelationService.saveBatch(originalPermissions);
        }
        return ResultTool.fail();
    }

    @PutMapping("/rolePermission")
    public JsonResult updateRolePermissionRelation(@RequestParam int id, @RequestParam int roleId, @RequestParam int permissionId) {
        return ResultTool.success(sysRolePermissionRelationService.updateById(new SysRolePermissionRelation(id, roleId, permissionId)));
    }
    @DeleteMapping("/rolePermission")
    public JsonResult deleteRolePermissionRelation(@RequestParam int id) {
        return ResultTool.success(sysRolePermissionRelationService.removeById(id));
    }
}
