package com.epam.web.mapper;

import com.epam.web.entities.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An abstract class {@code RowMapper} allows to map a row of the relations with the instance of entity class.
 *
 * @author Sviatlana Shelestava
 * @see com.epam.web.dao.AbstractDao
 * @since 1.0
 */
public interface RowMapper<T extends Entity> {

    /**
     * Iterates the ResultSet internally and adds it into the collection and builds an entity class object
     * Executes the given SQL statement, which returns a collection of mapped entities.
     *
     * @param resultSet represents a database result set, which is generated by executing a statement that queries the database
     * @return an entity of this application;
     * @throws SQLException if a database access error occurs or this method is
     *                      called on a closed result set
     */
    T map(ResultSet resultSet) throws SQLException;

}
