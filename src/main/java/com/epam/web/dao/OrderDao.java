package com.epam.web.dao;

import com.epam.web.entities.Order;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class OrderDao extends AbstractDao<Order> implements Dao<Order> {

    private static final String UPDATE_CURRENT_ORDER = "UPDATE track SET title=?, description=?, price=?, filename=? where id=?";

    private static final String INSERT_ORDER = "INSERT INTO track (id, title, description, price, filename) VALUE (?, ?, ?, ?, ?, ?, ?)";

    private static final String FIND_USER_ORDERS = "SELECT t.id, t.release_date, t.title, t.description, t.price, t.filename, " +
            "a.id, a.name FROM track t INNER JOIN track_artist ta ON (t.id = ta.track_id) INNER JOIN artist a ON (ta.artist_id=a.id) " +
            "WHERE t.id=?";
    private static final String FIND_ORDER_BY_ID = "SELECT t.id, t.release_date, t.title, t.description, t.price, t.filename, " +
            "a.id, a.name FROM track t INNER JOIN track_artist ta ON (t.id = ta.track_id) INNER JOIN artist a ON (ta.artist_id=a.id) " +
            "WHERE t.id=?";
    private static final String GET_USER_ORDER_STATUS = "SELECT pod.id, pod.order_date, pod.is_paid FROM purchase_order pod " +
            "INNER JOIN purchase_order_track p ON (p.order_id=pod.id) INNER JOIN track t ON (p.track_id=t.id) " +
            "WHERE pod.user_id = ? AND t.id = ?";

    public OrderDao(Connection connection, RowMapper<Order> mapper) {
        super(connection, mapper);
    }

    @Override
    public Optional<Order> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_ORDER_BY_ID, id);
    }

    public List<Order> getAllUserOrders(Long userId) throws DaoException {
        return executeQuery(FIND_USER_ORDERS, userId);
    }

    public Optional<Order> isPaidTrackByCurrentUser(Long userId, Long trackId) throws DaoException {
        return executeForSingleResult(GET_USER_ORDER_STATUS, userId, trackId);
    }

    @Override
    public void save(Order entity) {
    }

    @Override
    public void removeById(Long id) {
    }

    @Override   // todo: remove
    protected String getTableName() {
        return Order.TABLE;
    }
}
