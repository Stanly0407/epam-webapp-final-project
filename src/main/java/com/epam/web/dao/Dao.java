package com.epam.web.dao;


import com.epam.web.entities.Entity;
import com.epam.web.exceptions.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * An interface {@code Dao} defines the most common methods for making database queries.
 *
 * @author Sviatlana Shelestava
 * @see com.epam.web.dao.AbstractDao
 * @since 1.0
 */
public interface Dao<T extends Entity> {

    /**
     * Executes an SQL <code>SELECT</code> statement, which searches for an entity by id.
     *
     * @param id is a unique field of an entity in database
     * @return an <code>Optional</code> contains the entity entity with matching id
     * or <code>Optional</code> contain a null value;
     * @throws DaoException if a database access error occurs,
     *                      this method is called on a closed <code>PreparedStatement</code> and throw new DaoException.
     */
    Optional<T> getById(Long id) throws DaoException;

    /**
     * Executes the SQL <code>SELECT</code> statement, which returns
     * a collection of mapped entities from entity table in database.
     *
     * @return a collection <code>List</code> contains the entities produced by the given query or
     * empty collection <code>List</code>;
     * @throws DaoException if a database access error occurs or
     *                      this method is called on a closed <code>PreparedStatement</code>
     */
    List<T> getAll() throws DaoException;

    /**
     * Executes the SQL <code>DELETE</code> statement, which deletes an entity by id from database.
     *
     * @param id is a unique field of an entity in database
     * @throws DaoException if a database access error occurs or
     *                      this method is called on a closed <code>PreparedStatement</code>
     */
    void removeById(Long id) throws DaoException;


}
