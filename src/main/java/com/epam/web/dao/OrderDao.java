package com.epam.web.dao;

import com.epam.web.entities.Order;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class OrderDao extends AbstractDao<Order> implements Dao<Order> {

    private static final String INSERT_ORDER = "INSERT INTO purchase_order (user_id) VALUE (?)";
    private static final String FIND_PAID_ORDERS = "SELECT id, order_date, is_paid, user_id FROM purchase_order WHERE user_id = ? AND is_paid = true";
    private static final String FIND_ORDER_BY_ID = "SELECT id, order_date, is_paid, user_id FROM purchase_order WHERE id = ?";
    private static final String GET_USER_ORDER_STATUS = "SELECT pod.id, pod.order_date, pod.is_paid, pod.user_id FROM purchase_order pod " +
            "INNER JOIN purchase_order_track p ON (p.order_id=pod.id) INNER JOIN track t ON (p.track_id=t.id) WHERE pod.user_id = ? AND t.id = ?";
    private static final String FIND_UNPAID_ORDER_BY_USER_ID = "SELECT id, order_date, is_paid, user_id FROM purchase_order WHERE user_id = ? AND is_paid = false";
    private static final String INSERT_TRACK_IN_UNPAID_ORDER = "INSERT INTO purchase_order_track(order_id, track_id) value (?, ?)";
    private static final String DELETE_TRACK_FROM_ORDER = "DELETE FROM purchase_order_track WHERE order_id = ? AND track_id = ?";
    private static final String UPDATE_ORDER_STATUS = "UPDATE purchase_order SET is_paid = true where id = ?";
    private static final String DELETE_ORDER = "DELETE FROM purchase_order WHERE id=?";

    public OrderDao(Connection connection, RowMapper<Order> mapper) {
        super(connection, mapper);
    }

    public void createOrder(Long userId) throws DaoException {
        executeUpdate(INSERT_ORDER, userId);
    }

    public void addTrackToOrder(Long orderId, Long trackId) throws DaoException {
        executeUpdate(INSERT_TRACK_IN_UNPAID_ORDER, orderId, trackId);
    }

    public void updateOrderStatus(Long orderId) throws DaoException {
        executeUpdate(UPDATE_ORDER_STATUS, orderId);
    }

    public Optional<Order> getCurrentOrder(Long userId) throws DaoException {
        return executeForSingleResult(FIND_UNPAID_ORDER_BY_USER_ID, userId);
    }

    public void deleteTrackFromOrder(Long orderId, Long trackId) throws DaoException {
        executeUpdate(DELETE_TRACK_FROM_ORDER, orderId, trackId);
    }

    public List<Order> findPaidOrders(Long userId) throws DaoException {
        return executeQuery(FIND_PAID_ORDERS, userId);
    }

    public Optional<Order> getOrderStatusForTrack(Long userId, Long trackId) throws DaoException {
        return executeForSingleResult(GET_USER_ORDER_STATUS, userId, trackId);
    }

    @Override
    public Optional<Order> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_ORDER_BY_ID, id);
    }

    @Override
    public void removeById(Long id) throws DaoException {
        executeUpdate(DELETE_ORDER, id);
    }

    @Override
    protected String getTableName() {
        return Order.TABLE;
    }
}
