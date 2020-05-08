/*
 * Copyright (c) 2020
 */

package com.platform.security.service.impl;

import com.platform.security.entity.MessageBoard;
import com.platform.security.dao.MessageBoardDao;
import com.platform.security.service.MessageBoardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author code maker
 * @since 2020-05-06
 */
@Service
public class MessageBoardServiceImpl extends ServiceImpl<MessageBoardDao, MessageBoard> implements MessageBoardService {

}
