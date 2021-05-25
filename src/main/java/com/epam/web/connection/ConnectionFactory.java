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
    private static String[] PROPERTIES = new String[0];

    static {
        try {
            PROPERTIES = getConnectionProperties();
        } catch (IOException e) {
            LOGGER.error("ERROR (read properties): " + e + " | MESSAGE: " + e.getMessage());
        }
    }

    public static ProxyConnection create() throws DaoException {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Connection connection = DriverManager.getConnection(PROPERTIES[0], PROPERTIES[1], PROPERTIES[2]);
            return new ProxyConnection(connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static String[] getConnectionProperties() throws IOException {
        try (InputStream input = ConnectionFactory.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME)) {
            Properties properties = new Properties();
            properties.load(input);
            String url = properties.getProperty(PROPERTY_URL);
            String user = properties.getProperty(PROPERTIES_USER);
            String password = properties.getProperty(PROPERTIES_PASSWORD);
            return new String[]{url, user, password};
        } catch (IOException e) {
            throw new IOException(e.getMessage(), e);
        }

    }


}

