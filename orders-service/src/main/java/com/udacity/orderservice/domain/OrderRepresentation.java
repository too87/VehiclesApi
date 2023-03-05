package com.udacity.orderservice.domain;

import com.udacity.orderservice.client.Car;

public class OrderRepresentation {

    public Car car;

    private Long orderId;

    public OrderRepresentation(Car car, Long orderId) {
        this.car = car;
        this.orderId = orderId;
    }

    public Car getCar() {
        return car;
    }

    public OrderRepresentation setCar(Car car) {
        this.car = car;
        return this;
    }

    public Long getOrderId() {
        return orderId;
    }

    public OrderRepresentation setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }
}
