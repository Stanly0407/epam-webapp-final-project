package com.epam.web.dao;

import com.epam.web.entities.Entity;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Entity> implements Dao<T> {
    private static final Logger LOGGER = LogManager.getLogger(AbstractDao.class);

    private final Connection connection;
    private final RowMapper<T> mapper;

    public AbstractDao(Connection connection, RowMapper<T> mapper) {
        this.connection = connection;
        this.mapper = mapper;
    }

    protected List<T> executeQuery(String query, Object... params) throws DaoException {
        try (PreparedStatement preparedStatement = createStatement(query, params);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = mapper.map(resultSet);
                entities.add(entity);
            }
            return entities;
        } catch (SQLException e) {
            LOGGER.debug(this.getClass() + "  " + e.getMessage() + e);
            throw new DaoException(e);
        }
    }

    protected void executeUpdate(String query, Object... params) throws DaoException {
        try (PreparedStatement preparedStatement = createStatement(query, params)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.debug(this.getClass() + "  " + e.getMessage() + e);
            throw new DaoException(e);
        }
    }

    private PreparedStatement createStatement(String query, Object... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; ++i) {
            preparedStatement.setObject(i + 1, params[i]);
        }
    //    LOGGER.debug("preparedStatement " + preparedStatement);
        return preparedStatement;
    }

    public List<T> getAll() throws DaoException {
        String table = getTableName();
        return executeQuery("SELECT * FROM " + table);
    }

    protected Optional<T> executeForSingleResult(String query, Object... params) throws DaoException {
        List<T> items = executeQuery(query, params);
        if (items.size() == 1) {
            return Optional.of(items.get(0));
        } else if (items.size() > 1) {
            throw new IllegalArgumentException("More than one record was found");
        } else {
            return Optional.empty();
        }
    }

    protected abstract String getTableName();

}



