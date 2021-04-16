package com.epam.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDto {
    private Long id;
    private LocalDateTime orderDate;
    private int tracksAmount;
    private BigDecimal totalSum;

    public Long getId() {
        return id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public int getTracksAmount() {
        return tracksAmount;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public static class Builder {
        private OrderDto newOrder;

        public Builder() {
            newOrder = new OrderDto();
        }

        public Builder id(Long id) {
            newOrder.id = id;
            return this;
        }

        public Builder orderDate(LocalDateTime orderDate) {
            newOrder.orderDate = orderDate;
            return this;
        }

        public Builder tracksAmount(int tracksAmount) {
            newOrder.tracksAmount = tracksAmount;
            return this;
        }

        public Builder totalSum(BigDecimal totalSum) {
            newOrder.totalSum = totalSum;
            return this;
        }

        public OrderDto build() {
            return newOrder;
        }
    }

}
