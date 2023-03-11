package com.example.ApartmentRental.model.CrudRepositories;

import com.example.ApartmentRental.model.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    @Query(value =
            "SELECT payments.* FROM payments " +
            " JOIN apartments a on payments.apartment_apartment_id = a.apartment_id " +
            " JOIN apartment_buildings ab on ab.building_id = a.building_building_id " +
            " WHERE building_manager_username = ?1 AND paid_at is not null",
            nativeQuery = true)
    List<Payment> adminPayments(String adminUsername);
}
