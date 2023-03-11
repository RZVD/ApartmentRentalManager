package com.example.ApartmentRental.model.CrudRepositories;

import com.example.ApartmentRental.model.ApartmentBuilding;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentBuildingRepository extends CrudRepository<ApartmentBuilding, Integer> {
}
