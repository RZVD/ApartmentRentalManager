package com.example.ApartmentRental.model;

import com.example.ApartmentRental.model.user.Administrator;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "apartment_buildings")
public class ApartmentBuilding {
    @Id
    @Column(name = "building_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer buildingId;
    private String buildingName;
    private String buildingDescription;

    private String Address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Administrator buildingManager;

    public ApartmentBuilding() {
    }

    public ApartmentBuilding(String buildingName, String buildingDescription, String address, Administrator buildingManager, String buildingPhone) {
        this();
        this.setBuildingName(buildingName);
        this.setBuildingDescription(buildingDescription);
        this.setAddress(address);
        this.setBuildingManager(buildingManager);
        this.setBuildingPhone(buildingPhone);
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingDescription() {
        return buildingDescription;
    }

    public void setBuildingDescription(String buildingDescription) {
        this.buildingDescription = buildingDescription;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Administrator getBuildingManager() {
        return buildingManager;
    }

    public void setBuildingManager(Administrator buildingManager) {
        this.buildingManager = buildingManager;
    }

    public String getBuildingPhone() {
        return buildingPhone;
    }

    public void setBuildingPhone(String buildingPhone) {
        this.buildingPhone = buildingPhone;
    }

    public Set<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(Set<Apartment> apartments) {
        this.apartments = apartments;
    }

    private String buildingPhone;

    @OneToMany(mappedBy = "building")
    private Set<Apartment> apartments = new HashSet<>(0);

}
