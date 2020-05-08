/*
 * Copyright (c) 2020
 */

package com.platform.security.controller;
/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 5:16 PM
 */

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.security.common.entity.JsonResult;
import com.platform.security.common.utils.ResultTool;
import com.platform.security.entity.SysRequestPathPermissionRelation;
import com.platform.security.service.SysRequestPathPermissionRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *@program: JWTtest
 *@description: to manage relations between paths and permissions
 *@author: Tianshi Chen
 *@create: 2020-05-01 17:16
 */
@RestController
public class RequestPathPermissionController {
    @Autowired
    private SysRequestPathPermissionRelationService sysRequestPathPermissionRelationService;
    @GetMapping("/pathPermission")
    public JsonResult getPathPermissionRelationList () {
        return ResultTool.success(sysRequestPathPermissionRelationService.list());
    }
    @PostMapping("/pathPermission")
    public JsonResult addPathPermissionRelation(@RequestParam int urlId, @RequestParam int permissionId) {
        return ResultTool.success(sysRequestPathPermissionRelationService.save(new SysRequestPathPermissionRelation(urlId, permissionId)));
    }

    @PostMapping("/pathPermissionByPathIds")
    public JsonResult updatePathPermissionRelation(@RequestParam int permissionId, @RequestParam String pathIds) {
        System.out.println("before " + pathIds);
        List<Integer> list = JSON.parseArray(pathIds, Integer.class);
        QueryWrapper<SysRequestPathPermissionRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("permission_id", permissionId);
        // first to query the original permissions
        List<SysRequestPathPermissionRelation> originalPaths = sysRequestPathPermissionRelationService.list(queryWrapper);
        System.out.println("original: " + originalPaths);
        // construct all the new relations
        List<SysRequestPathPermissionRelation> toSave = new ArrayList<>();
        for (Integer item: list) {
            toSave.add(new SysRequestPathPermissionRelation(item, permissionId));
        }
        System.out.println("original: " + toSave);
        // remove all the permission with this role
        if ((sysRequestPathPermissionRelationService.count(queryWrapper) == 0 || sysRequestPathPermissionRelationService.remove(queryWrapper)) && sysRequestPathPermissionRelationService.saveBatch(toSave)) {
            return ResultTool.success();
        }
        else {
            sysRequestPathPermissionRelationService.remove(queryWrapper);
            sysRequestPathPermissionRelationService.saveBatch(originalPaths);
        }
        return ResultTool.fail();
    }

    @PutMapping("/pathPermission")
    public JsonResult updatePathPermissionRelation(@RequestParam int id, @RequestParam int urlId, @RequestParam int permissionId) {
        return ResultTool.success(sysRequestPathPermissionRelationService.updateById(new SysRequestPathPermissionRelation(id, urlId, permissionId)));
    }
    @DeleteMapping("/pathPermission")
    public JsonResult deletePathPermissionRelation(@RequestParam int id) {
        return ResultTool.success(sysRequestPathPermissionRelationService.removeById(id));
    }
}
