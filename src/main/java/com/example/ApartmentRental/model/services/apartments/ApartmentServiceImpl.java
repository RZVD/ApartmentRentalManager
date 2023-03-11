package com.example.ApartmentRental.model.services.apartments;

import com.example.ApartmentRental.model.Apartment;
import com.example.ApartmentRental.model.CrudRepositories.ApartmentRepository;
import com.example.ApartmentRental.model.services.payments.PaymentService;
import com.example.ApartmentRental.model.services.repairs.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApartmentServiceImpl implements ApartmentService{

    @Autowired
    private ApartmentRepository repository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    RepairService repairService;

    @Override
    public void save(Apartment apartment) {
        repository.save(apartment);
    }

    @Override
    public void remove(Apartment apartment) {

        for(var payment : apartment.getPayments()){
            paymentService.remove(payment);
        }
        for(var repair : apartment.getHistoryOfRepairs()) {
            repairService.remove(repair);
        }
        repository.delete(apartment);
    }

    @Override
    public List<Apartment> getAll() {
        return (List<Apartment>) repository.findAll();
    }

    @Override
    public Optional<Apartment> findById(Integer id) {
        return repository.findById(id);
    }
}
