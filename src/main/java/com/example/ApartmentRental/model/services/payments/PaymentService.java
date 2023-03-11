package com.example.ApartmentRental.model.services.payments;

import com.example.ApartmentRental.model.Payment;

import java.util.List;

public interface PaymentService {
    public void save(Payment payment);
    public void remove(Payment payment);
    public List<Payment> getAll();

    public List<Payment> getAdminPayments(String adminUsername);
}
