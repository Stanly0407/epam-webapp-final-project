package com.epam.web.service;

import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.OrderDao;
import com.epam.web.dao.TrackDao;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Order;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrderService {


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
            Optional<Order> optionalOrder = orderDao.getCurrentOrder(userId);
            Long orderId;
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                orderId = order.getId();
            } else {
                orderDao.createOrder(userId);
                Optional<Order> optionalNewOrder = orderDao.getCurrentOrder(userId);
                Order order = optionalNewOrder.get();
                orderId = order.getId();
            }
            // проверка добавлен ли трек уже в корзину, чтобы не было повтора. не удалять
            Optional<Track> optionalTrack = trackDao.getTrackFromCart(userId, trackId);
            if (!optionalTrack.isPresent()) {
                orderDao.addTrackToOrder(trackId, userId, orderId);
            }
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
        return  orderedTracks
                .stream()
                .map(TrackDto::getPrice)
                .reduce(BigDecimal::add)
                .get();
    }


}
