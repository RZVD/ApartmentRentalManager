package com.example.ApartmentRental.model.services.payments;

import com.example.ApartmentRental.model.CrudRepositories.PaymentRepository;
import com.example.ApartmentRental.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository repository;

    @Override
    public void save(Payment payment) {
        repository.save(payment);
    }

    @Override
    public void remove(Payment payment) {
        repository.delete(payment);
    }

    @Override
    public List<Payment> getAll() {
        return (List<Payment>) repository.findAll();
    }

    @Override
    public List<Payment> getAdminPayments(String adminUsername) {
        return repository.adminPayments(adminUsername);
    }
}
