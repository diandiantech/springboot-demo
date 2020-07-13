package com.dinglevin.demo.dal.dao;

import com.dinglevin.demo.dal.dataobject.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDAO {
    /**
     * 插入用户记录
     *
     * @param userDO
     * @return
     */
    Integer insert(UserDO userDO);

    /**
     * 根据userId查询用户记录
     *
     * @param userId
     * @return
     */
    UserDO queryUserById(@Param("userId") Long userId);

    /**
     * 根据name查询用户记录
     *
     * @param name
     * @return
     */
    UserDO queryUserByName(@Param("name") String name);

    /**
     * 查询所有用户记录
     *
     * @return
     */
    List<UserDO> queryAllUsers();
}
