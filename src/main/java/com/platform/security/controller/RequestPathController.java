/*
 * Copyright (c) 2020
 */

package com.platform.security.controller;
/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 5:11 PM
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.security.common.entity.JsonResult;
import com.platform.security.common.utils.ResultTool;
import com.platform.security.entity.SysRequestPath;
import com.platform.security.service.SysRequestPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

/**
 *@program: JWTtest
 *@description: to add path to be used in path
 *@author: Tianshi Chen
 *@create: 2020-05-01 17:11
 */
@RestController
@EnableAutoConfiguration
public class RequestPathController {
    @Autowired
    private SysRequestPathService sysRequestPathService;

    @GetMapping("/path")
    public JsonResult getPathListPage (@RequestParam(required = false) String pageNo, @RequestParam(required = false) String pageSize) {
        if (pageNo != null || pageSize != null) {
            assert pageNo != null;
            IPage<SysRequestPath> page = new Page<>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
            QueryWrapper<SysRequestPath> wrapper = new QueryWrapper<>();
            Object paths = sysRequestPathService.page(page, wrapper);
            System.out.println("get paths: " + paths);
            return ResultTool.success(paths);
        } else {
            return ResultTool.success(sysRequestPathService.list());
        }
    }

    @GetMapping("/path/number")
    public JsonResult getPathNum () {
        return ResultTool.success(sysRequestPathService.count());
    }

    @PostMapping("/path")
    public JsonResult addPath(@RequestParam String url, @RequestParam String description) {
        return ResultTool.success(sysRequestPathService.save(new SysRequestPath(url, description)));
    }
    @PutMapping("/path")
    public JsonResult updatePath(@RequestParam int id, @RequestParam String url, @RequestParam String description) {
        return ResultTool.success(sysRequestPathService.updateById(new SysRequestPath(id, url, description)));
    }
    @DeleteMapping("/path")
    public JsonResult deletePath(@RequestParam int id) {
        return ResultTool.success(sysRequestPathService.removeById(id));
    }
}
