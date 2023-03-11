package com.example.ApartmentRental.model.CrudRepositories;

import com.example.ApartmentRental.model.user.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
}
