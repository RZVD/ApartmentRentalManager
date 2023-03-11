package com.example.ApartmentRental.model.user;

public class UserFactory {
    public static User createUser(UserTypes typeOfUser, String username, String password, String legalName, String email) {
        User newUser = null;
        switch (typeOfUser) {
            case Tenant -> newUser = new Tenant();
            case Employee -> newUser = new Worker();
            case Administrator -> newUser = new Administrator();
        }

        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setLegalName(legalName);

        UserRole newRole = new UserRole(typeOfUser);
        newUser.getUserRoles().add(newRole);
        return newUser;
    }
}
