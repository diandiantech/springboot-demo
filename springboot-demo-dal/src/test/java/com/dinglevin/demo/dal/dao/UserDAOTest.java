package com.dinglevin.demo.dal.dao;

import com.dinglevin.demo.dal.dataobject.UserDO;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserDAOTest extends BaseDAOTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOTest.class);

    @Resource
    private UserDAO userDAO;

    @Test
    public void testAllSimple() {
        UserDO userDO = new UserDO();
        userDO.setName("test_user1");
        userDO.setPassword("test_pwd1");

        Integer inserted = userDAO.insert(userDO);
        assertEquals((Integer) 1, inserted);

        List<UserDO> userDOList = userDAO.queryAllUsers();
        LOGGER.info("testInsert queryAllUsers: {}", userDOList);
        assertEquals(1, userDOList.size());

        UserDO userDO1 = userDOList.get(0);
        assertEquals("test_user1", userDO1.getName());
        assertEquals("test_pwd1", userDO1.getPassword());

        UserDO userDO2 = userDAO.queryUserById(userDO1.getId());
        assertNotNull("queryUserById failed with null result", userDO2);
        assertEquals("test_user1", userDO2.getName());
        assertEquals("test_pwd1", userDO2.getPassword());

        UserDO userDO3 = userDAO.queryUserByName(userDO.getName());
        assertNotNull("queryUserByName failed with null result", userDO3);
        assertEquals("test_user1", userDO3.getName());
        assertEquals("test_pwd1", userDO3.getPassword());
    }
}
