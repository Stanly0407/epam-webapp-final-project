package com.epam.web.dao;

import com.epam.web.entities.User;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;
import com.epam.web.mapper.UserRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;


public class UserDao extends AbstractDao<User> implements Dao<User> {
    private static final Logger LOGGER = LogManager.getLogger(UserDao.class);

    private static final String FIND_BY_LOGIN_AND_PASSWORD = "SELECT id, login, name, lastname, role FROM user WHERE login = ? AND password = ?";

    public UserDao(Connection connection, RowMapper<User> mapper) {
        super(connection, mapper);
    }


    public Optional<User> findByLoginAndPassword(String login, String password) throws DaoException, SQLException {
        return executeForSingleResult(FIND_BY_LOGIN_AND_PASSWORD, new UserRowMapper(), login, password);
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.empty();
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
