package com.epam.web.service;

import com.epam.web.dao.*;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Order;
import com.epam.web.entities.Track;
import com.epam.web.entities.User;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);


    private DaoHelperFactory daoHelperFactory;

    public OrderService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public void deleteTrackFromCart(Long orderId, Long trackId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            orderDao.deleteTrackFromOrder(orderId, trackId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void addTrackToCart(Long userId, Long trackId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            TrackDao trackDao = daoHelper.createTrackDao();
            LOGGER.debug("getCurrentOrder userId = " + userId);
            Optional<Order> optionalOrder = orderDao.getCurrentOrder(userId);
            Long orderId;
            if (optionalOrder.isPresent()) {
                LOGGER.debug("optionalOrder.get() = " + optionalOrder.get());
                Order order = optionalOrder.get();
                orderId = order.getId();
            } else {
                orderDao.createOrder(userId);
                Optional<Order> optionalNewOrder = orderDao.getCurrentOrder(userId);
                LOGGER.debug("optionalNewOrder.get() = " + optionalNewOrder.get());
                Order order = optionalNewOrder.get();
                orderId = order.getId();
            }
            LOGGER.debug("orderId = " + orderId);
            // проверка добавлен ли трек уже в корзину, чтобы не было повтора. не удалять
            Optional<Track> optionalTrack = trackDao.getTrackFromCart(userId, trackId);
            if (!optionalTrack.isPresent()) {
                LOGGER.debug("!optionalTrack.isPresent() = true");
                orderDao.addTrackToOrder(orderId, trackId);
            }
        } catch (DaoException e) {
            LOGGER.debug(e + " ________ " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    public boolean payOrder(Long orderId, Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            TrackDao trackDao = daoHelper.createTrackDao();
            UserDao userDao = daoHelper.createUserDao();
            boolean payResult = false;
            List<Track> tracks = trackDao.findOrderedTracks(userId);
            BigDecimal orderAmount = tracks.stream().map(Track::getPrice).reduce(BigDecimal::add).get();
            Optional<User> userOptional = userDao.getById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                BigDecimal userBalance = user.getBalance();
                BigDecimal balanceAfterPay = userBalance.subtract(orderAmount); //new balance
                BigDecimal emptyBalance = new BigDecimal(0);
                if (balanceAfterPay.compareTo(emptyBalance) >= 0) {
                    userDao.updateUserBalance(balanceAfterPay, userId);
                    orderDao.updateOrderStatus(orderId);
                    payResult = true;
                } else {
                    payResult = false;
                }
            }
            LOGGER.debug("payResult === " + payResult);
            return payResult;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Long getCurrentCartId(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            Optional<Order> optionalOrder = orderDao.getCurrentOrder(userId);
            Long orderId = null;
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                orderId = order.getId();
            }
            return orderId;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public BigDecimal sumOfOrderedTracks(List<TrackDto> orderedTracks) {
        return orderedTracks
                .stream()
                .map(TrackDto::getPrice)
                .reduce(BigDecimal::add)
                .get();
    }


}
