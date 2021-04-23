/*
 * Copyright 2019-2020 Levin
 */
package com.dinglevin.demo.auth;

import com.dinglevin.demo.model.PermissionEnum;
import com.dinglevin.demo.model.RolePermission;
import com.dinglevin.demo.model.User;
import com.dinglevin.demo.model.UserRole;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.ListUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 描述：
 *
 * @author dinglevin
 * @date 2020/10/29 10:59 上午
 */
public class MemoryUserUtils {
    private MemoryUserUtils() { }

    private static final AtomicLong ID_GENERATOR = new AtomicLong(1000);

    private static final Map<String, User> USER_REPOSITORY = Maps.newHashMap();
    private static final Map<Long, UserRole> USER_ROLE_REPOSITORY = Maps.newHashMap();
    private static final Map<Long, List<RolePermission>> ROLE_PERMISSION_REPOSITORY = Maps.newHashMap();

    static {
        init();
    }

    public static User getUser(String name) {
        return USER_REPOSITORY.get(name);
    }

    public static UserRole getUserRole(Long roleId) {
        return USER_ROLE_REPOSITORY.get(roleId);
    }

    public static List<RolePermission> getRolePermissionList(Long roleId) {
        List<RolePermission> list = ROLE_PERMISSION_REPOSITORY.get(roleId);
        if (list == null) {
            return ListUtils.EMPTY_LIST;
        }
        return Collections.unmodifiableList(list);
    }

    private static void init() {
        UserRole adminRole = addUserRole("admin");
        UserRole premiumRole = addUserRole("premium");
        UserRole normalRole = addUserRole("normal");

        addUser("admin", "1234", adminRole);
        addUser("dinglevin", "1234", premiumRole);
        addUser("diandian", "1234", normalRole);

        addRolePermission(adminRole, "/protected/admin/userMgr", PermissionEnum.READ);
        addRolePermission(adminRole, "/protected/admin/userList", PermissionEnum.READ);

        addRolePermission(premiumRole, "/protected/premium/test1", PermissionEnum.READ);
        addRolePermission(premiumRole, "/protected/premium/test2", PermissionEnum.READ);

        addRolePermission(normalRole, "/protected/normal/test3", PermissionEnum.READ);
        addRolePermission(normalRole, "/protected/normal/test4", PermissionEnum.READ);
    }

    private static User addUser(String name, String password, UserRole userRole) {
        return addUser(createUse(name, password, userRole.getId()));
    }

    private static User addUser(User user) {
        USER_REPOSITORY.put(user.getName(), user);
        return user;
    }

    private static UserRole addUserRole(String name) {
        return addUserRole(createUserRole(name));
    }

    private static UserRole addUserRole(UserRole userRole) {
        USER_ROLE_REPOSITORY.put(userRole.getId(), userRole);
        return userRole;
    }

    private static RolePermission addRolePermission(UserRole userRole, String resource, PermissionEnum permission) {
        return addRolePermission(createRolePermission(userRole.getId(), resource, permission));
    }

    private static RolePermission addRolePermission(RolePermission rolePermission) {
        List<RolePermission> rolePermissionList = ROLE_PERMISSION_REPOSITORY.get(rolePermission.getUserRoleId());
        if (rolePermissionList == null) {
            rolePermissionList = Lists.newArrayList();
            ROLE_PERMISSION_REPOSITORY.put(rolePermission.getUserRoleId(), rolePermissionList);
        }
        rolePermissionList.add(rolePermission);
        return rolePermission;
    }

    private static User createUse(String name, String password, Long roleId) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setRoleId(roleId);
        user.setId(nextId());
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());
        return user;
    }

    private static UserRole createUserRole(String name) {
        UserRole userRole = new UserRole();
        userRole.setName(name);
        userRole.setId(nextId());
        userRole.setGmtCreate(new Date());
        userRole.setGmtModified(new Date());
        return userRole;
    }

    private static RolePermission createRolePermission(Long userRoleId, String resource, PermissionEnum permission) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setUserRoleId(userRoleId);
        rolePermission.setResource(resource);
        rolePermission.setPermission(permission);
        rolePermission.setId(nextId());
        rolePermission.setGmtCreate(new Date());
        rolePermission.setGmtModified(new Date());
        return rolePermission;
    }

    private static Long nextId() {
        return ID_GENERATOR.incrementAndGet();
    }
}
