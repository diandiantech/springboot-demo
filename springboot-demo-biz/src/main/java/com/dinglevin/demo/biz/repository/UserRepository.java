package com.dinglevin.demo.biz.repository;

import com.dinglevin.demo.model.User;

import java.util.List;

public interface UserRepository {
    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    User add(User user);

    /**
     * 根据name查询用户
     *
     * @param name
     * @return
     */
    User findUserByName(String name);

    /**
     * 查询所有用户
     *
     * @return
     */
    List<User> findAllUsers();
}
