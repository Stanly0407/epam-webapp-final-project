package com.epam.web.dao;


import com.epam.web.entities.Entity;
import com.epam.web.exceptions.DaoException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao <T extends Entity> {

    Optional<T> getById(Long id) throws DaoException;

    List<T> getAll() throws DaoException, SQLException;

    void save(T entity);

    void removeById(Long id);


}
