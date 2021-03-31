package com.epam.web.connection;

import com.epam.web.exceptions.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static final int POOL_SIZE = 5;
    private BlockingQueue<ProxyConnection> proxyConnections;

    private ConnectionPool(final int size)  {
      try{
        proxyConnections = new ArrayBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            ProxyConnection connection = ConnectionFactory.create();
            proxyConnections.offer(connection);
        }
        LOGGER.debug(" new proxyConnections -+-+-+-+-+- " + proxyConnections);
    }catch (IOException | DaoException e){

      }
    }

    private static class ConnectionPoolHolder {
        private static final ConnectionPool CONNECTION_POOL = new ConnectionPool(POOL_SIZE);
    }

    public static ConnectionPool getInstance() {
        LOGGER.debug("getInstance /-/-/-/-/ " + ConnectionPoolHolder.CONNECTION_POOL);
        return ConnectionPoolHolder.CONNECTION_POOL;
    }


    public ProxyConnection getConnection() throws InterruptedException {
        BlockingQueue<ProxyConnection> pool = getInstance().proxyConnections;
        LOGGER.debug("getConnection / proxyConnections ===" + getInstance().proxyConnections.size());
        return pool.take();
    }

    public void closeConnection(ProxyConnection connection)  { // returnConnection
        BlockingQueue<ProxyConnection> pool = getInstance().proxyConnections;
        LOGGER.debug("before closeConnection " + getInstance().proxyConnections.size());
        pool.offer(connection);
    }

    @Override
    public String toString() {
        return "ConnectionPool{" +
                "proxyConnections=" + proxyConnections +
                '}';
    }
}
