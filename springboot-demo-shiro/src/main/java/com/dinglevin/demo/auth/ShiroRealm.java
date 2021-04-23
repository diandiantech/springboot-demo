/*
 * Copyright 2019-2020 Levin
 */
package com.dinglevin.demo.auth;

import com.dinglevin.demo.model.RolePermission;
import com.dinglevin.demo.model.User;
import com.dinglevin.demo.model.UserRole;
import com.google.common.collect.Sets;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

/**
 * 描述：鉴权与认证
 *
 * @author dinglevin
 * @date 2020/10/27 8:51 下午
 */
public class ShiroRealm extends AuthorizingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        User user = getUser(username);
        if (user == null) {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }

        return new SimpleAuthenticationInfo(username, user.getPassword().toCharArray(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        String username = (String) getAvailablePrincipal(principals);
        UserRole userRole = getUserRole(username);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(Sets.newHashSet(userRole.getName()));
        for (RolePermission permission : getRolePermissions(userRole)) {
            authorizationInfo.addStringPermission(permission.getResource());
        }
        return authorizationInfo;
    }

    private User getUser(String username) {
        return MemoryUserUtils.getUser(username);
    }

    private UserRole getUserRole(String username) {
        User user = getUser(username);
        if (user == null) {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }

        UserRole userRole = MemoryUserUtils.getUserRole(user.getRoleId());
        if (userRole == null) {
            throw new UnknownAccountException("No account role found with user: " + user);
        }
        return userRole;
    }

    private List<RolePermission> getRolePermissions(UserRole userRole) {
        return MemoryUserUtils.getRolePermissionList(userRole.getId());
    }
}
