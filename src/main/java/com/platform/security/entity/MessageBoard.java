/*
 * Copyright (c) 2020
 */

package com.platform.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author code maker
 * @since 2020-05-06
 */
public class MessageBoard implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private LocalDateTime time;

    private String speaker;

    private String content;

    public MessageBoard(String speaker, String content) {
        setTime(LocalDateTime.now());
        this.speaker = speaker;
        this.content = content;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MessageBoard{" +
        "id=" + id +
        ", time=" + time +
        ", speaker=" + speaker +
        ", content=" + content +
        "}";
    }
}
