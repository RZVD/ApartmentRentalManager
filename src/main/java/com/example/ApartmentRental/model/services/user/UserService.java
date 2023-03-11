package com.example.ApartmentRental.model.services.user;

import com.example.ApartmentRental.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public void save(User user);
    public void remove(User user);
    public void register(User user);
    public List<User> getAll();
    public Optional<User> findByUsername(String username);
}
