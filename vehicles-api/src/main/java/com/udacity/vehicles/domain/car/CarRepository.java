package com.udacity.vehicles.domain.car;

import com.udacity.vehicles.domain.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select c from Car c where c.condition=:condition " +
            "and c.details.modelYear=:year and c.details.manufacturer.code=:model and c.orderId=0")
    Optional<List<Car>> search(@Param("condition")Condition condition,
                               @Param("year")int year,
                               @Param("model")int model);
}
