package com.epam.web.connection;

import com.epam.web.exceptions.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionFactory.class);
    private static final String PROPERTIES_FILENAME = "database.properties";
    private static final String URL = "jdbc:mysql://localhost:3306/music_wizard";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";


    public static ProxyConnection create() throws DaoException, IOException {
        Properties properties = new Properties();
     //   Reader fileReader = new FileReader(PROPERTIES_FILENAME);
      //  properties.load(fileReader);
      //  String url = properties.getProperty(URL);
      //  String username = properties.getProperty(USERNAME);
      //  String password = properties.getProperty(PASSWORD);
       properties.setProperty(PASSWORD, "root");
        //  String url = properties.getProperty("jdbc:mysql://localhost:3306/music_wizard?serverTimezone=UTC&characterEncoding=UTF-8");
       properties.setProperty(USERNAME, "root");
       // String password = properties.getProperty("root");
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Connection connection = DriverManager.getConnection(URL, properties);
            LOGGER.debug("Connection = " + connection);
             return new ProxyConnection(connection);
            //      return new ProxyConnection(connection, ConnectionPool.getInstance());
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
            throw new DaoException(e);
        }
    }

    //try (InputStream input = new FileInputStream("src//resources/db.properties")) {
    //            Properties properties = new Properties();
    //            properties.load(input);
    //
    //            CONNECTION_URL = properties.getProperty("CONNECTION_URL");
    //            USER = properties.getProperty("USER");
    //            PASS = properties.getProperty("PASS");
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }


}
