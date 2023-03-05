package com.udacity.orderservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.modelmapper.ModelMapper;

import java.util.Objects;

@Component
public class VehicleClient {
    private static final Logger log = LoggerFactory.getLogger(VehicleClient.class);
    private final WebClient client;
    private final ModelMapper mapper;

    public VehicleClient(WebClient client, ModelMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    public Car getCar(Long vehicleId, Car newCar) {
        try {
            Car car = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/cars/" + vehicleId)
                            .build()
                    )
                    .retrieve().bodyToMono(Car.class).block();

            mapper.map(Objects.requireNonNull(car), newCar);

            return newCar;
        } catch (Exception e) {
            log.warn("Car service is down");
            return newCar;
        }
    }

    public Car findCar(Condition condition,
                       Manufacturer.Brands manufacturer,
                       int modelYear, Car newCar) {
        try {
            Car car = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/cars/find")
                            .queryParam("condition", condition)
                            .queryParam("model", manufacturer.label)
                            .queryParam("year", modelYear)
                            .build()
                    )
                    .retrieve().bodyToMono(Car.class).block();

            mapper.map(Objects.requireNonNull(car), newCar);

            return newCar;
        } catch (Exception e) {
            log.warn("Car service is down");
            return newCar;
        }
    }

    public Car addCarOrder(Long vehicleId, Long orderId) {
        Car addedCar = new Car();
        try {
            Car car = client
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/cars/" + vehicleId + "/order/" + orderId)
                            .build()
                    )
                    .retrieve().bodyToMono(Car.class).block();

            mapper.map(Objects.requireNonNull(car), addedCar);

            return addedCar;
        } catch (Exception e) {
            log.warn("Car service is down");
            return addedCar;
        }
    }
}
