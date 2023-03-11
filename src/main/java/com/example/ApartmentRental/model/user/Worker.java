package com.example.ApartmentRental.model.user;

import com.example.ApartmentRental.model.Repair;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "workers")
@PrimaryKeyJoinColumn(name = "username")
public class Worker extends User{
    @ManyToMany(fetch = FetchType.LAZY)
    public Set<Repair> historyOfRepairs = new HashSet<>();
}
