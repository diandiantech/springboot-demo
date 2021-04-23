/*
 * Copyright 2019-2020 Levin
 */
package com.dinglevin.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends BaseModel {
    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 描述
     */
    private String description;
}
