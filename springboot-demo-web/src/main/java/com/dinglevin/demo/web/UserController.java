package com.dinglevin.demo.web;

import com.dinglevin.demo.biz.repository.UserRepository;
import com.dinglevin.demo.client.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserRepository userRepository;

    @GetMapping("add")
    public User add(@RequestParam("userName") String userName,
                    @RequestParam("password") String password) {
        User user = new User();
        user.setName(userName);
        user.setPassword(password);

        User newUser = userRepository.add(user);
        LOGGER.info("User added: {}", newUser);

        return newUser;
    }

    @GetMapping("findByName")
    public User findByName(@RequestParam("userName") String userName) {
        checkNotNull(userName, "userName is null");

        User user = userRepository.findUserByName(userName);
        LOGGER.info("findByName, userName: {}, result: {}", userName, user);

        return user;
    }

    @GetMapping("findAll")
    public List<User> findAll() {
        List<User> userList = userRepository.findAllUsers();
        LOGGER.info("findAll size: {}", userList == null ? 0 : userList.size());
        return userList;
    }
}
