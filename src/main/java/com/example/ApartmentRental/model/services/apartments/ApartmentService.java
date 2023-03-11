package com.example.ApartmentRental.model.services.apartments;

import com.example.ApartmentRental.model.Apartment;

import java.util.List;
import java.util.Optional;

public interface ApartmentService {
    public void save(Apartment apartment);
    public void remove(Apartment apartment);
    public List<Apartment> getAll();

    public Optional<Apartment> findById(Integer id);
}
