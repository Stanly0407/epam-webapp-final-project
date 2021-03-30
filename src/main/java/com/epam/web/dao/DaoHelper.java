package com.epam.web.dao;

import com.epam.web.connection.ConnectionPool;
import com.epam.web.connection.ProxyConnection;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.UserRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class DaoHelper implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger(DaoHelper.class);

    private ProxyConnection proxyConnection;

    public DaoHelper(ConnectionPool connectionPool) throws InterruptedException {
        LOGGER.debug("  ++++ this.proxyConnection = connectionPool.getConnection();" + connectionPool.getConnection() );
        this.proxyConnection = connectionPool.getConnection();
    }

    public UserDao createUserDao() {
        LOGGER.debug("was called createUserDao ***  return new UserDaoImpl(proxyConnection)");
        UserRowMapper userRowMapper = new UserRowMapper();
        return new UserDao(proxyConnection, userRowMapper);
    }

//    public OrderDaoImpl createOrderDao(){
//        return new OrderDaoImpl(proxyConnection);
//    }

    @Override
    public void close() throws DaoException {
        try {
            proxyConnection.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void startTransaction() throws DaoException {
        try {
            proxyConnection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
