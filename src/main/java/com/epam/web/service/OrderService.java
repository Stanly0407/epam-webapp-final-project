package com.epam.web.service;

import com.epam.web.dao.*;
import com.epam.web.dto.OrderDto;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Bonus;
import com.epam.web.entities.Order;
import com.epam.web.entities.Track;
import com.epam.web.entities.User;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);
    private static final int PERCENT = 100;

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
            // проверка добавлен ли трек уже в корзину, чтобы не было повтора. (не удалять)
            Optional<Track> optionalTrack = trackDao.getTrackFromCart(userId, trackId);
            if (!optionalTrack.isPresent()) {
                orderDao.addTrackToOrder(orderId, trackId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e, e.getMessage());
        }
    }

    public boolean payOrder(Long orderId, Long userId, boolean activatedDiscountBonus, boolean activatedFreeTracksBonus) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            UserDao userDao = daoHelper.createUserDao();
            BonusDao bonusDao = daoHelper.createBonusDao();

            TrackDao trackDao = daoHelper.createTrackDao();
            List<Track> tracks = trackDao.findOrderedTracks(userId);

            boolean payResult = false;
            Bonus discount = null;
            Bonus freeTracks = null;
            // определение итоговой суммы платежа
            if (activatedDiscountBonus) {
                Optional<Bonus> discountOptional = bonusDao.getUnusedDiscountBonus(userId);
                discount = discountOptional.get();
            }
            if (activatedFreeTracksBonus) {
                Optional<Bonus> freeTracksOptional = bonusDao.getUnusedFreeTracksBonus(userId);
                freeTracks = freeTracksOptional.get();
            }

            BigDecimal orderAmount = countOrderTotalSum(tracks, discount, freeTracks);

            // оплата
            Optional<User> userOptional = userDao.getById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                BigDecimal userBalance = user.getBalance();
                BigDecimal balanceAfterPay = userBalance.subtract(orderAmount); //new balance
                BigDecimal emptyBalance = new BigDecimal(0);
                if (balanceAfterPay.compareTo(emptyBalance) >= 0) {
                    daoHelper.startTransaction();
                    userDao.updateUserBalance(balanceAfterPay, userId);
                    orderDao.updateOrderStatus(orderId);
                    if (discount != null) {
                        bonusDao.changeUserBonusStatus(discount.getId());
                    } else if (freeTracks != null) {
                        bonusDao.changeUserBonusStatus(freeTracks.getId());
                    }
                    daoHelper.endTransaction();
                    payResult = true;
                } else {
                    payResult = false;
                }
            }
            return payResult;
        } catch (
                DaoException e) {
            throw new ServiceException(e);
        }
    }


    public boolean checkFreeTracksBonus(Long userId, Long bonusFreeTracksId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            TrackDao trackDao = daoHelper.createTrackDao();
            BonusDao bonusDao = daoHelper.createBonusDao();
            List<Track> tracks = trackDao.findOrderedTracks(userId);
            int freeTracksAmount = 0;
            Optional<Bonus> bonusOptional = bonusDao.getById(bonusFreeTracksId);
            if (bonusOptional.isPresent()) {
                Bonus bonus = bonusOptional.get();
                freeTracksAmount = bonus.getAmount();
            }
            return freeTracksAmount >= tracks.size();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    public List<OrderDto> getPaidOrders(Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            OrderDao orderDao = daoHelper.createOrderDao();
            TrackDao trackDao = daoHelper.createTrackDao();
            List<Order> paidOrdersList = orderDao.findPaidOrders(userId);
            List<OrderDto> paidOrders = new ArrayList<>();
            for (Order order : paidOrdersList) {
                Long orderId = order.getId();
                List<Track> tracks = trackDao.findPaidTracksByOrderId(orderId);
                BigDecimal totalSum = tracks.stream().map(Track::getPrice).reduce(BigDecimal::add).get();
                int paidTracksAmount = tracks.size();
                OrderDto orderDto = createOrderDto(order, totalSum, paidTracksAmount);
                paidOrders.add(orderDto);
            }
            return paidOrders;
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

    public BigDecimal countOrderTotalSum(List<Track> tracks, Bonus discount, Bonus freeTracks) {
        if (freeTracks != null) {
            int freeTracksAmount = freeTracks.getAmount();
            tracks.subList(0, freeTracksAmount).clear();
        }
        BigDecimal total = sumOfOrderedTracks(tracks);
        if (discount != null) {
            BigDecimal discountAmount = new BigDecimal(discount.getAmount() / PERCENT);
            total = total.multiply(discountAmount);
        }
        return total;
    }

    public BigDecimal sumOfOrderedTracks(List<Track> orderedTracks) {
        LOGGER.debug("sumOfOrderedTracks ////// orderedTracks " + orderedTracks);
        return orderedTracks
                .stream()
                .map(Track::getPrice)
                .reduce(BigDecimal::add)
                .get();
    }

    private OrderDto createOrderDto(Order order, BigDecimal totalSum, int paidTracksAmount) throws DaoException {
        Long id = order.getId();
        LocalDateTime orderDate = order.getOrderDate();
        return new OrderDto.Builder()
                .id(id)
                .orderDate(orderDate)
                .tracksAmount(paidTracksAmount)
                .totalSum(totalSum)
                .build();
    }

}
