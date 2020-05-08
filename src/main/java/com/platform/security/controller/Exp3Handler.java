/*
 * Copyright (c) 2020
 */

package com.platform.security.controller;
/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 1:36 PM
 */

import com.platform.security.common.entity.JsonResult;
import com.platform.security.common.utils.ResultTool;
import com.platform.security.dao.SQLInjectionMapper;
import com.platform.security.entity.MessageBoard;
import com.platform.security.service.MessageBoardService;
import com.platform.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import com.platform.security.entity.User;

import java.util.List;

/**
 *@program: JWTtest
 *@description: exp3 api
 *@author: Tianshi Chen
 *@create: 2020-04-30 13:36
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/seminar3")
public class Exp3Handler {
    @Autowired
    private SQLInjectionMapper sqlInjectionMapper;

    @Autowired
    UserService userService;

    @Autowired
    MessageBoardService messageBoardService;

    @PostMapping("/exp1")
    public JsonResult SQLInjection(@RequestParam String sql) {
        System.out.println("now the sql is " + sql);
        System.out.println("now the result is " + sqlInjectionMapper.executeSQL(sql));
        return ResultTool.success(sqlInjectionMapper.executeSQL(sql).toString());
    }

    @GetMapping("/exp1")
    public JsonResult queryWithSQLInjection(@RequestParam String id) {
        System.out.println("now the sql is select * from user where id = " + id);
        List<User> temp = userService.selectById(id);
        System.out.println("now the result is " + temp);
        return ResultTool.success(temp);
    }

    @PostMapping("/exp2")
    public JsonResult postMessageXSSInjection(@RequestParam String speaker,@RequestParam String content) {
        return ResultTool.success(messageBoardService.save(new MessageBoard(speaker, content)));
    }

    @GetMapping("/exp2")
    public JsonResult getMessagesXSSInjection() {
        return ResultTool.success(messageBoardService.list());
    }

    @DeleteMapping("/exp2")
    public JsonResult deleteMessageXSSInjection(@RequestParam String id) {
        return ResultTool.success(messageBoardService.removeById(id));
    }
}
