package com.example.ApartmentRental.model.services.apartment_buildings;

import com.example.ApartmentRental.model.ApartmentBuilding;
import com.example.ApartmentRental.model.CrudRepositories.ApartmentBuildingRepository;
import com.example.ApartmentRental.model.services.apartments.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApartmentBuildingServiceImpl implements ApartmentBuildingService {

    @Autowired
    private ApartmentBuildingRepository apartmentBuildingRepository;

    @Autowired
    private ApartmentService apartmentService;

    @Override
    public void save(ApartmentBuilding building) {
        apartmentBuildingRepository.save(building);
    }

    @Override
    public void remove(ApartmentBuilding building) {
        for (var apartment : building.getApartments()) {
            apartmentService.remove(apartment);
        }

        apartmentBuildingRepository.delete(building);
    }

    @Override
    public List<ApartmentBuilding> getAll() {
        return (List<ApartmentBuilding>) apartmentBuildingRepository.findAll();
    }

    @Override
    public Optional<ApartmentBuilding> findById(Integer id) {
        return apartmentBuildingRepository.findById(id);
    }
}
