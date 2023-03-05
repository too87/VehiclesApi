package com.udacity.pricing.repository;

import com.udacity.pricing.entity.Price;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;


public interface PriceRepository extends CrudRepository<Price, Long> {

    @Query("Select p from Price p where p.vehicleId=:id")
    Optional<Price> findPriceByVehicleId(@Param("id")Long vehicleId);

    @Transactional
    @Modifying
    @Query("delete from Price p where p.vehicleId=:id")
    void deleteByVehicleId(@Param("id")Long vehicleId);

}
