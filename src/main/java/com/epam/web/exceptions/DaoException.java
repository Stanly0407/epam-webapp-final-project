package com.epam.web.exceptions;

import java.sql.SQLException;

public class DaoException extends Exception {

    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

//    public DaoException(String message, Throwable cause) {
//        super(message, cause);
//    }

    public DaoException(SQLException e) {
    }
}
