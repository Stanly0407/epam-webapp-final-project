package com.epam.web.exceptions;

import org.apache.commons.fileupload.FileUploadException;

public class ServiceException extends Exception {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(DaoException e) {

    }

    public ServiceException(DaoException e, String message) {
    }

    public ServiceException(FileUploadException e, String message) {
    }

    public ServiceException(Exception e, String message) {
    }
}
