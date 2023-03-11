package com.example.ApartmentRental.model.user;
import com.example.ApartmentRental.model.ApartmentBuilding;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "managers")
@PrimaryKeyJoinColumn(name = "username")
public class Administrator extends User{
    @OneToMany(mappedBy = "buildingManager")
    private Set<ApartmentBuilding> buildings = new HashSet<>();
}
