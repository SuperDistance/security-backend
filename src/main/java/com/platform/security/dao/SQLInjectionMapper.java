/*
 * Copyright (c) 2020
 */

package com.platform.security.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resources;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 2:27 PM
 */
@Service
@Mapper
public interface SQLInjectionMapper {
    @Select("${sqlStr}")
    List<LinkedHashMap<String, Object>> executeSQL(@Param(value="sqlStr") String sqlStr);
}
