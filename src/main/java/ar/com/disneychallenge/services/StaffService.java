package ar.com.disneychallenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.disneychallenge.entities.Staff;
import ar.com.disneychallenge.repos.StaffRepository;

@Service
public class StaffService {

    @Autowired
    StaffRepository repo;

    public void crearStaff(Staff staff) {
        repo.save(staff);
    }
}
