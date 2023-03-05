package com.udacity.orderservice.api;

import com.udacity.orderservice.client.Condition;
import com.udacity.orderservice.client.Manufacturer;
import com.udacity.orderservice.domain.OrderRepresentation;
import com.udacity.orderservice.entity.Order;
import com.udacity.orderservice.service.OrderService;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Creates a list to store any vehicles.
     * @return list of vehicles
     */
    @GetMapping
    ResponseEntity<List<OrderRepresentation>> list() {
        List<OrderRepresentation> resources = orderService.list();
        return ResponseEntity.ok(resources);
    }

    /**
     * Posts information to create a new vehicle's order in the system.
     * @param condition Vehicle's manufacturer to order.
     * @param manufacturer Vehicle's condition to order.
     * @param modelYear Vehicle's model year to order.
     * @return response that the new order was added to the system
     * @throws URISyntaxException if the request contains invalid fields or syntax
     */
    @PostMapping
    ResponseEntity<OrderRepresentation> createOrder(@Valid @RequestParam Condition condition,
                                  @RequestParam Manufacturer.Brands manufacturer,
                                  @RequestParam int modelYear) throws URISyntaxException {
        OrderRepresentation savedOrder = orderService.createOrder(condition, manufacturer, modelYear);
        return ResponseEntity.created(new URI(savedOrder.getOrderId().toString())).body(savedOrder);
    }

    /**
     * Gets information of a specific order by ID.
     * @param id the id number of the given order
     * @return all information for the requested order
     */
    @GetMapping("/{id}")
    ResponseEntity<OrderRepresentation> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.get(id));
    }

}
