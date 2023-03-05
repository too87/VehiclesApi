package com.udacity.orderservice.api;

import com.udacity.orderservice.client.Car;
import com.udacity.orderservice.domain.OrderRepresentation;
import com.udacity.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderRepresentation toOrderRepresentation(Long id, Car car) {
        return new OrderRepresentation(car, id);
    }
}
