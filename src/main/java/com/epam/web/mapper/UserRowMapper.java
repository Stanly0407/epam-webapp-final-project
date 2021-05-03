package com.epam.web.mapper;

import com.epam.web.entities.Role;
import com.epam.web.entities.User;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(User.ID);
        String login = resultSet.getString(User.LOGIN);
        String name = resultSet.getString(User.NAME);
        String lastname = resultSet.getString(User.LASTNAME);
        String role = resultSet.getString(User.ROLE);
        BigDecimal balance = resultSet.getBigDecimal(User.BALANCE);
        boolean status = resultSet.getBoolean(User.STATUS);
        return new User(id, login, name, lastname, Role.valueOf(role), balance, status);
    }

}
