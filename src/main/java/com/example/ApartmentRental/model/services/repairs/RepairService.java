package com.example.ApartmentRental.model.services.repairs;

import com.example.ApartmentRental.model.Repair;

import java.util.List;

public interface RepairService {

    public void save(Repair repair);
    public void remove(Repair repair);
    public List<Repair> getAll();
    public List<Repair> getAdminRepairs(String adminUsername);
    public List<Repair> getWorkerRepairs(String workerUsername);

}
