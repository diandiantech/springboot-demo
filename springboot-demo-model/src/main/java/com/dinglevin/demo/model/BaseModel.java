/*
 * Copyright 2019-2020 Levin
 */
package com.dinglevin.demo.model;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 描述：模型基类
 *
 * @author dinglevin
 * @date 2020/10/29 10:43 上午
 */
@Getter
@Setter
public class BaseModel {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
