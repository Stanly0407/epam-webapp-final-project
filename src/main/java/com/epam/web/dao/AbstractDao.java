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

/**
 * An abstract class {@code AbstractDao} for data access objects (DAOs),
 * providing methods to access the entities and the connection.
 *
 * @author Sviatlana Shelestava
 * @see com.epam.web.dao.Dao
 * @since 1.0
 */
public abstract class AbstractDao<T extends Entity> implements Dao<T> {
    private static final Logger LOGGER = LogManager.getLogger(AbstractDao.class);

    /**
     * The field to determine the connection with this webapp database. SQL statements
     * are executed and results are returned within the context of this connection.
     */
    private final Connection connection;

    /**
     * The field to determine the specific mappers for subclasses of this {@code AbstractDao}
     * abstract class and allows to map a row of the relations with the instance of entity class.
     *
     * @see com.epam.web.mapper.RowMapper
     */
    private final RowMapper<T> mapper;

    /**
     * Sole constructor. (For invocation by subclass constructors.)
     */
    public AbstractDao(Connection connection, RowMapper<T> mapper) {
        this.connection = connection;
        this.mapper = mapper;
    }

    /**
     * Executes the given SQL statement, which returns a collection of mapped entities.
     *
     * @param query  an SQL statement to be sent to the database, typically an SQL <code>SELECT</code> statement
     * @param params any number of parameters for setting the designated to the given query for execute
     * @return a collection <code>List</code> contains the entities produced by the given query or
     * empty collection <code>List</code>;
     * @throws DaoException if a database access error occurs,
     *                      this method is called on a closed <code>PreparedStatement</code> and throw new DaoException.
     */
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

    /**
     * Executes the SQL statement, which must be an SQL Data Manipulation Language (DML) statement,
     * such as <code>INSERT</code>, <code>UPDATE</code> or <code>DELETE</code>.
     *
     * @param query  an SQL statement to be sent to the database.
     * @param params any number of parameters for setting the designated to the given query for execute.
     * @throws DaoException if a database access error occurs,
     *                      this method is called on a closed <code>PreparedStatement</code> and throw new DaoException.
     */
    protected void executeUpdate(String query, Object... params) throws DaoException {
        try (PreparedStatement preparedStatement = createStatement(query, params)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.debug(this.getClass() + "  " + e.getMessage() + e);
            throw new DaoException(e);
        }
    }

    /**
     * Sets the designated query to the given param values.
     *
     * @param query  an SQL statement to be sent to the database.
     * @param params any number of parameters for setting the designated to the given
     *               query for execute.
     * @throws SQLException if parameterIndex does not correspond to a parameter
     *                      * marker in the SQL statement; if a database access error occurs or
     *                      * this method is called on a closed <code>PreparedStatement</code>
     */
    private PreparedStatement createStatement(String query, Object... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; ++i) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        LOGGER.debug("preparedStatement " + preparedStatement);
        return preparedStatement;
    }

    /**
     * Executes the SQL <code>SELECT</code> statement, which returns
     * a collection of mapped entities from entity table in database.
     *
     * @return a collection <code>List</code> contains the entities produced by the given query or
     * empty collection <code>List</code>;
     * @throws DaoException if a database access error occurs or
     *                      this method is called on a closed <code>PreparedStatement</code>
     */
    public List<T> getAll() throws DaoException {
        String table = getTableName();
        return executeQuery("SELECT * FROM " + table);
    }

    /**
     * Executes the given SQL statement, which returns an Optional on mapped entity.
     *
     * @param query  an SQL statement to be sent to the database, typically an SQL <code>SELECT</code> statement
     * @param params any number of parameters for setting the designated to the given
     *               query for execute
     * @return an <code>Optional</code> contains the entity produced by the given query
     * or <code>Optional</code> contain a null value;
     * @throws DaoException if a database access error occurs,
     *                      this method is called on a closed <code>PreparedStatement</code> and throw new DaoException.
     */
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

    /**
     * Returns the name of entity in database for SQL statement
     *
     * @return a name of entity in database
     */
    protected abstract String getTableName();

}



