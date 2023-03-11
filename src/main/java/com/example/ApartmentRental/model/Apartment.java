package com.example.ApartmentRental.model;

import com.example.ApartmentRental.model.user.Tenant;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer apartmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    private ApartmentBuilding building;

    public Apartment(ApartmentBuilding building, Tenant tenant, Integer numberOfRooms, Double usableArea) {
        this.building = building;
        this.tenant = tenant;
        this.numberOfRooms = numberOfRooms;
        this.usableArea = usableArea;
    }

    private Integer apartmentNumber;

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public boolean needsPayment(){
        return payments
            .stream()
            .anyMatch(Payment::needsToBePaid);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Tenant tenant;

    private Integer numberOfRooms;

    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }

    public ApartmentBuilding getBuilding() {
        return building;
    }

    public void setBuilding(ApartmentBuilding building) {
        this.building = building;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public Double getUsableArea() {
        return usableArea;
    }

    public void setUsableArea(Double usableArea) {
        this.usableArea = usableArea;
    }

    public Set<Repair> getHistoryOfRepairs() {
        return historyOfRepairs;
    }

    public void setHistoryOfRepairs(Set<Repair> historyOfRepairs) {
        this.historyOfRepairs = historyOfRepairs;
    }

    private Double usableArea;

    @OneToMany(mappedBy = "apartment")
    private Set<Repair> historyOfRepairs = new HashSet<>(0);

    @OneToMany(mappedBy = "apartment")
    private Set<Payment> payments = new HashSet<>();

    public Apartment() {
    }
}
