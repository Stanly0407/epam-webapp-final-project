package com.epam.web.dao;

import com.epam.web.connection.ConnectionPool;
import com.epam.web.exceptions.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class DaoHelperFactory {
    // можно потом замокать и тестировать сервис отдельно

    private static final Logger LOGGER = LogManager.getLogger(DaoHelperFactory.class);

    public DaoHelper create() throws DaoException, IOException, InterruptedException {
        LOGGER.debug("Called method daoHelperFactory.create()  ----   ConnectionPool.getInstance()  ");

        return new DaoHelper(ConnectionPool.getInstance());
    }
}
