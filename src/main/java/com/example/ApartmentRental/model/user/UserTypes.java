package com.example.ApartmentRental.model.user;

import java.util.Random;

public enum UserTypes {
    Tenant, Administrator, Employee;
    static final Random rnd = new Random();
    public static UserTypes randomType(){
        UserTypes[] types = values();
        return types[rnd.nextInt(types.length)];
    }
}
