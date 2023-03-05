package com.udacity.boogle.maps.api;

import com.udacity.boogle.maps.entiry.Address;
import com.udacity.boogle.maps.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maps")
public class MapsController {
    private final AddressService addressService;

    public MapsController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<Address> get(@RequestParam Double lat,
                                       @RequestParam Double lon) {
        Address address = addressService.createRandomAddress(lat, lon);
        return  ResponseEntity.ok(address);
    }

}
