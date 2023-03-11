package com.example.ApartmentRental.model.services.apartment_buildings;

import com.example.ApartmentRental.model.ApartmentBuilding;

import java.util.List;
import java.util.Optional;

public interface ApartmentBuildingService {
    public void save(ApartmentBuilding building);

    public void remove(ApartmentBuilding buildings);

    public List<ApartmentBuilding> getAll();

    public Optional<ApartmentBuilding> findById(Integer id);
}
