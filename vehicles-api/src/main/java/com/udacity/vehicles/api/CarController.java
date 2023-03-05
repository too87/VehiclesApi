package com.udacity.vehicles.api;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import com.udacity.vehicles.service.CarService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Implements a REST-based controller for the Vehicles API.
 */
@RestController
@RequestMapping("/cars")
class CarController {

    private final CarService carService;
    private final CarResourceAssembler assembler;

    CarController(CarService carService, CarResourceAssembler assembler) {
        this.carService = carService;
        this.assembler = assembler;
    }

    /**
     * Creates a list to store any vehicles.
     * @return list of vehicles
     */
    @GetMapping
    Resources<Resource<Car>> list() {
        List<Resource<Car>> resources = carService.list().stream().map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(resources,
                linkTo(methodOn(CarController.class).list()).withSelfRel());
    }

    /**
     * Gets information of a specific car by ID.
     * @param id the id number of the given vehicle
     * @return all information for the requested vehicle
     */
    @GetMapping("/{id}")
    Resource<Car> get(@PathVariable Long id) {

        Car byId = carService.findById(id);
        return assembler.toResource(byId);
    }

    /**
     * Posts information to create a new vehicle in the system.
     * @param car A new vehicle to add to the system.
     * @return response that the new vehicle was added to the system
     * @throws URISyntaxException if the request contains invalid fields or syntax
     */
    @PostMapping
    ResponseEntity<?> post(@Valid @RequestBody Car car) throws URISyntaxException {
        Car savedCar = carService.save(car);
        Resource<Car> resource = assembler.toResource(savedCar);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    /**
     * Updates the information of a vehicle in the system.
     * @param id The ID number for which to update vehicle information.
     * @param car The updated information about the related vehicle.
     * @return response that the vehicle was updated in the system
     */
    @PutMapping("/{id}")
    ResponseEntity<?> put(@PathVariable Long id, @Valid @RequestBody Car car) {
        car.setId(id);
        Car updated = carService.save(car);
        Resource<Car> resource = assembler.toResource(updated);
        return ResponseEntity.ok(resource);
    }

    /**
     * Removes a vehicle from the system.
     * @param id The ID number of the vehicle to remove.
     * @return response that the related vehicle is no longer in the system
     */
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {

        carService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Search for a vehicle from the system.
     * @param condition The condition of the vehicle to search.
     * @param year The year of the vehicle to search.
     * @param model The year of the vehicle to search.
     * @return  all information for the requested vehicle
     */
    @GetMapping("/find")
    Resource<Car> find(@RequestParam Condition condition,
                       @RequestParam int year,
                       @RequestParam int model) {

        Car byId = carService.search(condition, year, model);
        return assembler.toResource(byId);
    }

    @PostMapping("/{id}/order/{orderId}")
    ResponseEntity<?> addOrder(@PathVariable Long id,@PathVariable Long orderId) throws URISyntaxException {
        Car savedCar = carService.addOrder(id, orderId);
        Resource<Car> resource = assembler.toResource(savedCar);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

}
