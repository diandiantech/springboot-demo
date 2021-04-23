package com.dinglevin.demo.biz.repository.impl;

import com.dinglevin.demo.biz.repository.UserRepository;
import com.dinglevin.demo.model.User;
import com.dinglevin.demo.dal.dao.UserDAO;
import com.dinglevin.demo.dal.dataobject.UserDO;
import com.dinglevin.demo.infra.utils.DozerBeanMapperUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

@Component
public class UserRepositoryImpl implements UserRepository {
    @Resource
    private UserDAO userDAO;

    @Override
    public User add(User user) {
        checkNotNull(user, "user is null");
        checkNotNull(user.getName(), "user.name is null");
        checkNotNull(user.getPassword(),"user.password is null");

        UserDO userDO = DozerBeanMapperUtils.map(user, UserDO.class);
        Integer inserted = userDAO.insert(userDO);
        checkState(inserted != null && inserted == 1, "insert user failed: " + userDO);

        User insertedUser = findUserByName(user.getName());
        checkState(insertedUser != null, "No user found after insert: " + user);
        return insertedUser;
    }

    @Override
    public User findUserByName(String name) {
        checkNotNull(name, "name is null");

        UserDO userDO = userDAO.queryUserByName(name);
        return DozerBeanMapperUtils.map(userDO, User.class);
    }

    @Override
    public List<User> findAllUsers() {
        List<UserDO> userDOList = userDAO.queryAllUsers();
        return DozerBeanMapperUtils.mapList(userDOList, User.class);
    }
}
