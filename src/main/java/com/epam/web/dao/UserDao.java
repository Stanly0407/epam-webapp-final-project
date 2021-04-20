package com.epam.web.dao;

import com.epam.web.entities.User;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Optional;


public class UserDao extends AbstractDao<User> implements Dao<User> {

    private static final String FIND_BY_LOGIN_AND_PASSWORD = "SELECT id, login, name, lastname, role, balance FROM user WHERE login = ? AND password = ?";
    private static final String FIND_USER_BY_ID = "SELECT id, login, name, lastname, role, balance FROM user WHERE id = ?";
    private static final String UPDATE_BALANCE = "UPDATE user SET balance = ? where id = ?";
    private static final String UPDATE_PASSWORD = "UPDATE user SET password = ? where id = ?";

    public UserDao(Connection connection, RowMapper<User> mapper) {
        super(connection, mapper);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) throws DaoException {
        return executeForSingleResult(FIND_BY_LOGIN_AND_PASSWORD, login, password);
    }

    @Override
    public Optional<User> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_USER_BY_ID, id);

    }

    public void updateUserBalance(BigDecimal paymentAmount, Long id) throws DaoException {
        executeUpdate(UPDATE_BALANCE, paymentAmount, id);
    }

    public void updatePassword(String newPassword, Long id) throws DaoException {
        executeUpdate(UPDATE_PASSWORD, newPassword, id);
    }

    @Override
    public void save(User entity) {
    }

    @Override
    public void removeById(Long id) {
    }

    @Override
    protected String getTableName() {
        return User.TABLE;
    }
}
