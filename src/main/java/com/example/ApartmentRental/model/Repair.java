package com.example.ApartmentRental.model;

import com.example.ApartmentRental.model.user.Tenant;
import com.example.ApartmentRental.model.user.Worker;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "repairs")
public class Repair {
    public Repair(Date timeOfRequest, Apartment apartment, Tenant requestedBy, String description) {
        this.timeOfRequest = timeOfRequest;
        this.apartment = apartment;
        this.requestedBy = requestedBy;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer repairId;

    public Integer getRepairId() {
        return repairId;
    }

    public void setRepairId(Integer repairId) {
        this.repairId = repairId;
    }

    public Date getTimeOfRepair() {
        return timeOfRepair;
    }

    public void setTimeOfRepair(Date timeOfRepair) {
        this.timeOfRepair = timeOfRepair;
    }

    public Set<Worker> getRepairMen() {
        return repairMen;
    }

    public void setRepairMen(Set<Worker> repairMen) {
        this.repairMen = repairMen;
    }

    public Repair() {
    }

    public Date getTimeOfRequest() {
        return timeOfRequest;
    }

    public void setTimeOfRequest(Date timeOfRequest) {
        this.timeOfRequest = timeOfRequest;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Date timeOfRequest;
    private Date timeOfRepair;
    @ManyToOne(fetch = FetchType.LAZY)
    private Apartment apartment;

    @ManyToOne
    @JoinColumn(name = "requested_by")
    private Tenant requestedBy;

    @ManyToMany(mappedBy = "historyOfRepairs")
    private Set<Worker> repairMen = new HashSet<>(0);
    private String description;

    public Tenant getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Tenant requestedBy) {
        this.requestedBy = requestedBy;
    }
}
