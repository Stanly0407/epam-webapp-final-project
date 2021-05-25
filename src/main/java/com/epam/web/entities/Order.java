package com.epam.web.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order extends Entity {
    public static final String TABLE = "purchase_order";
    public static final String ID = "id";
    public static final String ORDER_DATE = "order_date";
    public static final String IS_PAID = "is_paid";
    public static final String USER_ID = "user_id";

    private LocalDateTime orderDate;
    private boolean isPaid;
    private Long userId;

    public Order() {
    }

    public Order(Long id, LocalDateTime orderDate, boolean isPaid, Long userId) {
        super(id);
        this.orderDate = orderDate;
        this.isPaid = isPaid;
        this.userId = userId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public boolean getPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return isPaid == order.isPaid &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(userId, order.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDate, isPaid, userId);
    }

}
