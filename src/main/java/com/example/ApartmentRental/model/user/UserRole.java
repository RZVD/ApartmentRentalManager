package com.example.ApartmentRental.model.user;

import jakarta.persistence.*;

@Entity
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;
    UserTypes role;

    public void setUser(User user) {
        this.user = user;
    }

    public UserTypes getRole() {
        return this.role;
    }

    public UserRole() {
    }

    public UserRole(User user, UserTypes role){
        this.user = user;
        this.role = role;
    }

    public UserRole(UserTypes role) {
        this.role = role;
    }

}
