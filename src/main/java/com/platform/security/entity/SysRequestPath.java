package com.platform.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 请求路径
 * </p>
 *
 * @author code maker
 * @since 2020-04-23
 */
public class SysRequestPath implements Serializable {

    public SysRequestPath(Integer id, String url, String description) {
        this.id = id;
        this.url = url;
        this.description = description;
    }

    public SysRequestPath(String url, String description) {
        this.url = url;
        this.description = description;
    }

    private static final long serialVersionUID=1L;

    /**
     * 主键id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 路径描述
     */
    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SysRequestPath{" +
        "id=" + id +
        ", url=" + url +
        ", description=" + description +
        "}";
    }
}
