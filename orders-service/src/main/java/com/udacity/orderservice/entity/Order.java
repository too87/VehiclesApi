package com.udacity.orderservice.entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long vehicleId;

    @CreatedDate
    private LocalDateTime orderDate;

    @CreatedDate
    private LocalDateTime modifyDate;

    public Long getId() {
        return id;
    }

    public Order setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public Order setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Order setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public Order setModifyDate(LocalDateTime modifyDate) {
        this.modifyDate = modifyDate;
        return this;
    }
}
