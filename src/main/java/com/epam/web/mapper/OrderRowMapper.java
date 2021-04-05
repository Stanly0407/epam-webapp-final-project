package com.epam.web.mapper;

import com.epam.web.entities.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Order.ID);
        LocalDateTime orderDate = resultSet.getObject(Order.ORDER_DATE, LocalDateTime.class);
        boolean isPaid = resultSet.getBoolean(Order.IS_PAID);
        Long userId = resultSet.getLong(Order.USER_ID);
        return new Order(id, orderDate, isPaid, userId);
    }
}
