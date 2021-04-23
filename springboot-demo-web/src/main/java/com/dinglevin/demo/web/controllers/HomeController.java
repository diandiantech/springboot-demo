/*
 * Copyright 2019-2020 Levin
 */
package com.dinglevin.demo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 描述：
 *
 * @author dinglevin
 * @date 2020/10/29 5:09 下午
 */
@Controller
public class HomeController {
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/success")
    public String success(Model model) {
        return "success";
    }
}
