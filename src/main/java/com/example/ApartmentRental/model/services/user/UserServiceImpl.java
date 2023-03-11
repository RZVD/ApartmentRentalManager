package com.example.ApartmentRental.model.services.user;

import com.example.ApartmentRental.model.CrudRepositories.UserRepository;
import com.example.ApartmentRental.model.CrudRepositories.UserRoleRepository;
import com.example.ApartmentRental.model.user.User;
import com.example.ApartmentRental.model.user.UserRole;
import com.example.ApartmentRental.model.user.UserTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);

        for (UserRole role : user.getUserRoles()) {
            role.setUser(user);
            userRoleRepository.save(role);
        }
    }

    public void save(User user, UserTypes types){
        var roles = user.getUserRoles();
        roles.add(new UserRole(user, types));
    }

    @Override
    public void remove(User user) {
        userRepository.delete(user);
    }

    @Override
    public void register(User user) {
        //TODO
    }

    @Override
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username){
        return userRepository.findById(username);
    }
}
