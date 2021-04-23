/*
 * Copyright 2019-2020 Levin
 */
package com.dinglevin.demo.auth;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述：
 *
 * @author dinglevin
 * @date 2020/10/29 4:45 下午
 */
@Controller
public class ProtectedController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtectedController.class);

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request) {
        LOGGER.info("login with username: {}, password: {}", username, password);

        String loginFailure = (String) request.getAttribute("shiroLoginFailure");
        if (loginFailure == null) {
            LOGGER.info("successfully login with username: {}, password: {}", username, password);
            return "success";
        } else {
            LOGGER.info("Failed login with username: {}, password: {}, message: {}", username, password, loginFailure);
            return "login";
        }
    }

    @GetMapping("/public/test")
    public String publicTest(Model model) {
        model.addAttribute("message", "publicTest");
        return "protected/test";
    }

    @RequiresRoles("admin")
    @GetMapping("/protected/admin/userMgr")
    public String userMgr(Model model) {
        model.addAttribute("message", "userMgr");
        return "protected/test";
    }

    @RequiresRoles("admin")
    @GetMapping("/protected/admin/userList")
    public String userList(Model model) {
        model.addAttribute("message", "userList");
        return "protected/test";
    }

    @RequiresRoles("premium")
    @GetMapping("/protected/premium/test1")
    public String test1(Model model) {
        model.addAttribute("message", "test1");
        return "protected/test";
    }

    @RequiresRoles("premium")
    @GetMapping("/protected/premium/test2")
    public String test2(Model model) {
        model.addAttribute("message", "test2");
        return "protected/test";
    }

    @RequiresRoles("normal")
    @GetMapping("/protected/normal/test3")
    public String test3(Model model) {
        model.addAttribute("message", "test3");
        return "protected/test";
    }

    @RequiresRoles("normal")
    @GetMapping("/protected/normal/test4")
    public String test4(Model model) {
        model.addAttribute("message", "test4");
        return "protected/test";
    }
}
