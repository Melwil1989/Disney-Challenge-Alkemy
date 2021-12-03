package ar.com.disneychallenge.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.disneychallenge.entities.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    
}
