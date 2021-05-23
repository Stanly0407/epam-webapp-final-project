package com.epam.web.dao;


import com.epam.web.connection.ConnectionFactory;
import com.epam.web.entities.Order;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.OrderRowMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;


public class OrderDaoTest {

    private static final String PROPERTIES_FILENAME = "testDatabase.properties";
    private static final String PROPERTY_URL = "CONNECTION_URL";
    private static final String PROPERTIES_USER = "USER";
    private static final String PROPERTIES_PASSWORD = "PASSWORD";
    private static Connection connection = null;
    private final OrderRowMapper mapper = new OrderRowMapper();
    private final OrderDao orderDao = new OrderDao(connection, mapper);
    private final Order testOrder = new Order(1L, LocalDateTime.of(2021, 5, 5, 0, 0, 0), false, 1L);
    private static final Long TEST_ID = 1L;

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
    public void findPaidOrdersTestShouldReturnEmptyList() throws DaoException {
        List<Order> actual = new ArrayList<>();
        List<Order> expected = orderDao.findPaidOrders(TEST_ID);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addToOrderDeleteTrackTest() throws DaoException {
        orderDao.addTrackToOrder(TEST_ID, TEST_ID);
        Optional<Order> actualOrder = orderDao.getCurrentOrder(TEST_ID);
        Order actual = actualOrder.get();
        Long actualId = actual.getId();
        Assert.assertEquals(actual, testOrder);
        orderDao.removeById(actualId);
    }

}