package com.example.ApartmentRental.model.user;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @Column(name = "username", unique = true, nullable = false, length = 45)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String legalName;
    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "user")
    private Set<UserRole> userRoles = new HashSet<>(0);

    public User() {
    }

    public User(String username, String password, String legalName, String email, Set<UserRole> userRoles) {
        this.username = username;
        this.password = password;
        this.legalName = legalName;
        this.email = email;
        this.userRoles = userRoles;
    }
    public Set<UserRole> getUserRoles() {
        return userRoles;
    }
    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}



