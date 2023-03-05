package com.udacity.boogle.maps.repository;

import com.udacity.boogle.maps.entiry.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {

    @Query("Select a from Address a where a.lat=:lat and a.lon=:lon")
    Optional<Address> findAddressByLatAndLon(@Param("lat")Double lat, @Param("lon")Double lon);

}
