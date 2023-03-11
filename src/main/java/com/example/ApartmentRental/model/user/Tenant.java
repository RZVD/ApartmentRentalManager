package com.example.ApartmentRental.model.user;

import com.example.ApartmentRental.model.Apartment;
import com.example.ApartmentRental.model.Repair;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "tenants")
@PrimaryKeyJoinColumn(name = "username")
public class Tenant extends User {
    @OneToMany(mappedBy = "tenant")
    private Set<Apartment> rentedApartments= new HashSet<>(0);

    @OneToMany(mappedBy = "requestedBy")
    private Set<Repair> requestedRepairs = new HashSet<>(0);

    public Tenant() {
    }
}