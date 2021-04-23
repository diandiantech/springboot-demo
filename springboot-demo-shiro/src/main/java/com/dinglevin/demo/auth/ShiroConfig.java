/*
 * Copyright 2019-2020 Levin
 */
package com.dinglevin.demo.auth;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

/**
 * 描述：Shiro相关配置
 *
 * @author dinglevin
 * @date 2020/10/27 8:42 下午
 */
@Configuration
@PropertySource("shiro.properties")
@Slf4j
public class ShiroConfig {
    @Bean
    public Realm realm() {
        return new ShiroRealm();
    }

    @Bean
    public ShiroFilterChainDefinition chainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/login", "authc");
        definition.addPathDefinition("/protected/**", "authc");
        return definition;
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleException(AuthorizationException e, Model model) {
        log.warn("AuthorizationException was thrown", e);

        Map<String, Object> map = Maps.newHashMap();
        map.put("status", HttpStatus.FORBIDDEN.value());
        map.put("message", "No message available");
        model.addAttribute("errors", map);

        return "403";
    }

    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception ex, Model model) {
        log.error("Error", ex);

        Map<String, Object> map = Maps.newHashMap();
        model.addAttribute("errors", map);

        return "error";
    }
}
