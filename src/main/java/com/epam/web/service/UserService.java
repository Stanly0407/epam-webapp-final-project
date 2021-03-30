package com.epam.web.service;

import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.UserDao;
import com.epam.web.entities.User;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    private DaoHelperFactory daoHelperFactory;

    public UserService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public Optional<User> login(String login, String password) throws ServiceException {
        LOGGER.debug("Called method login");

        try (DaoHelper daoHelper = daoHelperFactory.create()){
            LOGGER.debug("was called method daoHelperFactory.create()" + daoHelper);
            UserDao userDao = daoHelper.createUserDao();
            LOGGER.debug("return userDao.findByLoginAndPassword(login, password);");
            return userDao.findByLoginAndPassword(login, password);
        } catch (DaoException | IOException | InterruptedException | SQLException e) {
            LOGGER.debug("ServiceException === " + e.getMessage());
            throw new ServiceException((DaoException) e);
        }
    }

}
