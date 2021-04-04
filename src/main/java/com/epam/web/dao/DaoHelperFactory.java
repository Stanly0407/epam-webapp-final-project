package com.epam.web.dao;

import com.epam.web.connection.ConnectionPool;

public class DaoHelperFactory {
    // можно потом замокать и тестировать сервис отдельно

    public DaoHelper create() throws InterruptedException {
        return new DaoHelper(ConnectionPool.getInstance());
    }
}
