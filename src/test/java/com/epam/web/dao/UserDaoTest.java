package com.epam.web.dao;


import com.epam.web.connection.ConnectionFactory;
import com.epam.web.entities.Role;
import com.epam.web.entities.User;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.UserRowMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;


public class UserDaoTest {

    private static final String PROPERTIES_FILENAME = "testDatabase.properties";
    private static final String PROPERTY_URL = "CONNECTION_URL";
    private static final String PROPERTIES_USER = "USER";
    private static final String PROPERTIES_PASSWORD = "PASSWORD";
    private static Connection connection = null;
    private final UserRowMapper mapper = new UserRowMapper();
    private final UserDao userDao = new UserDao(connection, mapper);
    private final User testUser = new User(2L, "user", "User", "Userov", Role.USER, new BigDecimal("20.00"), false);
    private static final Long TEST_ID = 2L;

    @BeforeClass
    public static void initDatabase() {
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME)) {
            Properties properties = new Properties();
            properties.load(input);
            String url = properties.getProperty(PROPERTY_URL);
            String user = properties.getProperty(PROPERTIES_USER);
            String password = properties.getProperty(PROPERTIES_PASSWORD);
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(url, user, password);
        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void findByLoginAndPasswordTest() throws DaoException {
        Optional<User> actualUser = userDao.findByLoginAndPassword("user", "1111");
        User actual = actualUser.get();
        Assert.assertEquals(testUser, actual);
    }

    @Test
    public void getByIdTest() throws DaoException {
        Optional<User> userOptional = userDao.getById(TEST_ID);
        User actual = userOptional.get();
        Assert.assertEquals(testUser, actual);
    }

    @Test
    public void updateStatusTest() throws DaoException {
        User expected = new User(2L, "user", "User", "Userov", Role.USER, new BigDecimal("20.00"), true);
        userDao.updateStatus(true, TEST_ID);
        Optional<User> userOptional = userDao.getById(TEST_ID);
        User actual = userOptional.get();
        Assert.assertEquals(expected, actual);
        userDao.updateStatus(false, TEST_ID);
    }


}