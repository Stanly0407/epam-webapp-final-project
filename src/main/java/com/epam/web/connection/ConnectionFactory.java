package com.epam.web.connection;

import com.epam.web.exceptions.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionFactory.class);
    private static final String PROPERTIES_FILENAME = "database.properties";
    private static final String PROPERTY_URL = "CONNECTION_URL";
    private static final String PROPERTIES_USER = "USER";
    private static final String PROPERTIES_PASSWORD = "PASSWORD";
    private static String url;
    private static String user;
    private static String password;

    public ConnectionFactory() {
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME)) {
            Properties properties = new Properties();
            properties.load(input);
            url = properties.getProperty(PROPERTY_URL);
            user = properties.getProperty(PROPERTIES_USER);
            password = properties.getProperty(PROPERTIES_PASSWORD);
        } catch (IOException e) {
            LOGGER.error("ERROR (read properties): " + e + " | MESSAGE: " + e.getMessage());
        }
    }

    public ProxyConnection create() throws DaoException {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Connection connection = DriverManager.getConnection(url, user, password);
            return new ProxyConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


}

