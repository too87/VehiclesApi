package com.udacity.orderservice.service;

import com.udacity.orderservice.client.Car;
import com.udacity.orderservice.client.Condition;
import com.udacity.orderservice.client.Manufacturer;
import com.udacity.orderservice.client.VehicleClient;
import com.udacity.orderservice.domain.OrderRepresentation;
import com.udacity.orderservice.entity.Order;
import com.udacity.orderservice.repository.OrderRepository;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class OrderService {

    private final VehicleClient vehicleClient;

    private final OrderRepository orderRepository;
    public OrderService(VehicleClient vehicleClient, OrderRepository orderRepository) {
        this.vehicleClient = vehicleClient;
        this.orderRepository = orderRepository;
    }

    public OrderRepresentation createOrder(Condition condition, Manufacturer.Brands manufacturer, int modelYear) {
        Car car = new Car();

        vehicleClient.findCar(condition, manufacturer, modelYear, car);
        if (isNull(car.getId())) {
            throw new CarNotFoundException();
        }
        Order newOrder = orderRepository.save(new Order()
                .setVehicleId(car.getId())
                .setOrderDate(LocalDateTime.now())
                .setModifyDate(LocalDateTime.now()));
        // Update Vehicle API with orderID
        vehicleClient.addCarOrder(car.getId(), newOrder.getId());
        return new OrderRepresentation(car, newOrder.getId());
    }

    public OrderRepresentation get(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new OrderNotFoundException();
        }

        Car car = new Car();
        vehicleClient.getCar(order.get().getVehicleId(), car);

        if (isNull(car.getId())) {
            throw new CarNotFoundException();
        }
        return new OrderRepresentation(car, id);
    }

    public List<OrderRepresentation> list() {
        return orderRepository.findAll().stream().map(order -> {
            Car car = new Car();
            vehicleClient.getCar(order.getVehicleId(), car);
            return new OrderRepresentation(car, order.getId());
        }).filter(order -> !isNull(order.getCar().getId()))
                .collect(Collectors.toList());
    }

}
