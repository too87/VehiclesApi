package com.udacity.pricing.api;

import com.udacity.pricing.entity.Price;
import com.udacity.pricing.service.PriceException;
import com.udacity.pricing.service.PricingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Implements a REST-based controller for the pricing service.
 */
@RestController
@RequestMapping("/services/price")
public class PricingController {

    private final PricingService pricingService;

    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    /**
     * Gets the price for a requested vehicle.
     * @param vehicleId ID number of the vehicle for which the price is requested
     * @return price of the vehicle, or error that it was not found.
     */
    @GetMapping
    public Price get(@RequestParam Long vehicleId) {
        try {
            return pricingService.getPrice(vehicleId);
        } catch (PriceException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Price Not Found", e);
        }
    }

    @PostMapping
    public ResponseEntity<Price> create(@RequestParam Long vehicleId) throws URISyntaxException {
        Price price = pricingService.createPrice(vehicleId);
        return ResponseEntity.created(new URI(price.getId().toString())).body(price);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long vehicleId) {
        try {
            pricingService.deletePrice(vehicleId);
            return ResponseEntity.noContent().build();
        } catch (PriceException e) {
            return ResponseEntity.notFound().build();
        }

    }
}
