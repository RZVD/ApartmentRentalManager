package com.example.ApartmentRental.model.CrudRepositories;

import com.example.ApartmentRental.model.Repair;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairRepository extends CrudRepository<Repair, Integer> {
    @Query(value =
            "SELECT repairs.* FROM repairs " +
            "JOIN apartments a on a.apartment_id = repairs.apartment_apartment_id " +
            "JOIN apartment_buildings ab on ab.building_id = a.building_building_id " +
            "WHERE ab.building_manager_username = ?1 AND time_of_repair is null",
        nativeQuery = true)
    List<Repair> adminManualQuery(String adminUsername);

    @Query(value =
            "Select repairs.* " +
            "FROM repairs JOIN workers_history_of_repairs whor on repairs.repair_id = whor.history_of_repairs_repair_id " +
            "WHERE whor.repair_men_username = ?1 AND repairs.time_of_repair IS NULL",
            nativeQuery = true)
    List<Repair> workerManualQuery(String adminUsername);
}
