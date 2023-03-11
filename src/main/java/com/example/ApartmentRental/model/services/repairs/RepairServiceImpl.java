package com.example.ApartmentRental.model.services.repairs;

import com.example.ApartmentRental.model.CrudRepositories.RepairRepository;
import com.example.ApartmentRental.model.Repair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairServiceImpl implements RepairService{

    @Autowired
    private RepairRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void save(Repair repair) {
        repository.save(repair);
    }

    @Override
    public void remove(Repair repair) {
        jdbcTemplate.execute(
            "DELETE FROM workers_history_of_repairs " +
            "WHERE history_of_repairs_repair_id = " + repair.getRepairId()
        );
        repository.delete(repair);

    }

    @Override
    public List<Repair> getAll() {
        return (List<Repair>) repository.findAll();
    }
    @Override
    public List<Repair> getAdminRepairs(String adminUsername){
        return repository.adminManualQuery(adminUsername);
    }

    @Override
    public List<Repair> getWorkerRepairs(String workerUsername) {
        return repository.workerManualQuery(workerUsername);
    }

}
