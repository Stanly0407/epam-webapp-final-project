package com.epam.web.connection;

import com.epam.web.exceptions.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static final int POOL_SIZE = 5;
    private ConnectionFactory factory;
    private BlockingQueue<ProxyConnection> proxyConnections;

    private ConnectionPool(final int size) {
        try {
            factory = new ConnectionFactory();
            proxyConnections = new ArrayBlockingQueue<>(size, true);
            for (int i = 0; i < size; i++) {
                ProxyConnection connection = factory.create();
                proxyConnections.offer(connection);
            }
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private static class ConnectionPoolHolder {
        private static final ConnectionPool CONNECTION_POOL = new ConnectionPool(POOL_SIZE);
    }

    public static ConnectionPool getInstance() {
        return ConnectionPoolHolder.CONNECTION_POOL;
    }

    public ProxyConnection getConnection() {
        ProxyConnection proxyConnection = null;
        try {
            BlockingQueue<ProxyConnection> pool = getInstance().proxyConnections;
            proxyConnection = pool.take();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
        return proxyConnection;
    }

    public void closeConnection(ProxyConnection connection) {
        BlockingQueue<ProxyConnection> pool = getInstance().proxyConnections;
        pool.offer(connection);
    }

}
