package com.epam.web.connection;

import com.epam.web.exceptions.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static final int POOL_SIZE = 2;
    private final BlockingQueue<ProxyConnection> proxyConnections;

    private ConnectionPool(final int size) throws IOException, DaoException {
        proxyConnections = new ArrayBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            ProxyConnection connection = ConnectionFactory.create();
            proxyConnections.offer(connection);
        }
    }

    private static class ConnectionPoolHolder {
        private static ConnectionPool CONNECTION_POOL;
        static {
            try {
                CONNECTION_POOL = new ConnectionPool(POOL_SIZE);
            } catch (IOException | DaoException e) {
                e.printStackTrace();
            }
        }
    }

    public static ConnectionPool getInstance() {
        LOGGER.debug("ConnectionPool getInstance");
        return ConnectionPoolHolder.CONNECTION_POOL;
    }


    public ProxyConnection getConnection() throws InterruptedException {
        BlockingQueue<ProxyConnection> pool = getInstance().proxyConnections;
        LOGGER.debug("getConnection");
        return pool.take();
    }

    public void closeConnection(ProxyConnection connection)  { // returnConnection
        BlockingQueue<ProxyConnection> pool = getInstance().proxyConnections;
        LOGGER.debug("closeConnection");
        pool.offer(connection);
    }

}
