/*
 * Copyright 2019-2020 Levin
 */
package com.dinglevin.demo.model;

/**
 * 描述：权限枚举
 *
 * @author dinglevin
 * @date 2020/10/29 10:49 上午
 */
public enum PermissionEnum {
    READ("r", "只读"),
    WRITE("w", "只写"),
    READ_WRITE("rw", "读写");

    private String code;
    private String desc;

    PermissionEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static PermissionEnum of(String code) {
        for (PermissionEnum permission : PermissionEnum.values()) {
            if (permission.getCode().equalsIgnoreCase(code)) {
                return permission;
            }
        }
        throw new IllegalStateException("Permission code not recognized: " + code);
    }
}
