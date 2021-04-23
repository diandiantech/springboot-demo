/*
 * Copyright 2019-2020 Levin
 */
package com.dinglevin.demo.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 描述：角色权限定义
 *
 * @author dinglevin
 * @date 2020/10/29 10:45 上午
 */
@Getter
@Setter
public class RolePermission extends BaseModel {
    /**
     * 用户角色ID
     */
    private Long userRoleId;

    /**
     * 资源名
     */
    private String resource;

    /**
     * 拥有权限
     */
    private PermissionEnum permission;

    /**
     * 描述
     */
    private String description;
}
