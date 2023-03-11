package com.example.ApartmentRental.model.CrudRepositories;

import com.example.ApartmentRental.model.Apartment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends CrudRepository<Apartment, Integer> {
}