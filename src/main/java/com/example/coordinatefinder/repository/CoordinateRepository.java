package com.example.coordinatefinder.repository;

import com.example.coordinatefinder.entity.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CoordinateRepository extends JpaRepository <Coordinate, UUID> {

    Optional<Coordinate> findCoordinateByLatitudeAndLongitudeAndRadius(String latitude, String longitude, String radius);

}
