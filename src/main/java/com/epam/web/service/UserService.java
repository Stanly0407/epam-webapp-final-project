package com.epam.web.service;

import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.UserDao;
import com.epam.web.entities.User;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Optional;

public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    private DaoHelperFactory daoHelperFactory;

    public UserService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public Optional<User> login(String login, String password) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            return userDao.findByLoginAndPassword(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<User> getUserInfo(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            return userDao.getById(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void topUpUserBalance(String paymentAmountString, Long id) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();
            //проверка данных карты, если не соответствует выводить ошибку платежа... pattern
            //проверка соответствует ли paymentAmount формату BigDecimal

            Optional<User> userOptional = userDao.getById(id);
            User user = userOptional.get();
            BigDecimal balance = user.getBalance();
            BigDecimal paymentAmount = new BigDecimal(paymentAmountString);
            BigDecimal updatedUserBalance = balance.add(paymentAmount);
            userDao.updateUserBalance(updatedUserBalance, id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}
