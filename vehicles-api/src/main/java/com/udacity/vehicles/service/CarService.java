package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.Price;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private static final Logger log = LoggerFactory.getLogger(CarService.class);
    private final CarRepository repository;
    private final MapsClient mapsClient;
    private final PriceClient priceClient;

    public CarService(CarRepository repository,
                      MapsClient mapsClient,
                      PriceClient priceClient) {

        this.mapsClient = mapsClient;
        this.priceClient = priceClient;
        this.repository = repository;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        Optional<Car> foundCar = repository.findById(id);
        if (foundCar.isEmpty()) {
            throw new CarNotFoundException();
        }
        Car car = foundCar.get();

        String price = priceClient.getPrice(car.getId());
        car.setPrice(price);

        Location address = mapsClient.getAddress(car.getLocation());
        car.setLocation(address);

        return car;
    }

    /**
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setModifiedAt(LocalDateTime.now());
                        carToBeUpdated.setCondition(car.getCondition());
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setPrice(priceClient.getPrice(car.getId()));
                        carToBeUpdated.setLocation(mapsClient.getAddress(car.getLocation()));
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        Car newCar = repository.save(car);
        Location address = mapsClient.getAddress(car.getLocation());
        newCar.setLocation(address);

        String price = priceClient.createPrice(car.getId());
        newCar.setPrice(price);
        return repository.save(newCar);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        Optional<Car> foundCar = repository.findById(id);
        if (foundCar.isEmpty()) {
            throw new CarNotFoundException();
        }
        repository.delete(foundCar.get());
        priceClient.delete(foundCar.get().getId());
    }

    public Car search(Condition condition, int year, int model) {
        Optional<List<Car>> foundCar = repository.search(condition, year, model);
        if (foundCar.isEmpty()) {
            throw new CarNotFoundException();
        }
        return foundCar.get().get(0);
    }

    public Car addOrder(Long id, Long orderId) {
        Optional<Car> foundCar = repository.findById(id);
        if (foundCar.isEmpty()) {
            throw new CarNotFoundException();
        }

        return repository.save(foundCar.get().setOrderId(orderId));
    }

}
