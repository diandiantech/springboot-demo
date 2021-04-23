/*
 * Copyright 2019-2020 Levin
 */
package com.dinglevin.demo.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 描述：用户角色定义
 *
 * @author dinglevin
 * @date 2020/10/29 10:41 上午
 */
@Getter
@Setter
public class UserRole extends BaseModel {
    /**
     * 角色名
     */
    private String name;

    /**
     * 描述
     */
    private String description;
}
