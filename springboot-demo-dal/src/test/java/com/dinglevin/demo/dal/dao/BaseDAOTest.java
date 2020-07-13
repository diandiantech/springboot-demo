package com.dinglevin.demo.dal.dao;

import com.dinglevin.demo.dal.ApplicationTest;
import com.dinglevin.demo.dal.config.TestDalConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationTest.class, TestDalConfiguration.class})
public abstract class BaseDAOTest {
}
