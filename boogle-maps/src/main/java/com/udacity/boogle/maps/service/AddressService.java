package com.udacity.boogle.maps.service;

import com.udacity.boogle.maps.entiry.Address;
import com.udacity.boogle.maps.repository.AddressRepository;
import com.udacity.boogle.maps.repository.MockAddressRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final MockAddressRepository mockAddressRepository;
    public AddressService(AddressRepository addressRepository, MockAddressRepository mockAddressRepository) {
        this.addressRepository = addressRepository;
        this.mockAddressRepository = mockAddressRepository;
    }

    public Address createRandomAddress(Double lat,
                                       Double lon) {
        Optional<Address> addressByLatAndLon = addressRepository.findAddressByLatAndLon(lat, lon);

        if (addressByLatAndLon.isPresent()) {
            return addressByLatAndLon.get();
        }

        Address address = mockAddressRepository.getRandom(lat, lon);
        address.setLat(lat);
        address.setLon(lon);
        return addressRepository.save(address);
    }

}
