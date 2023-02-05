package com.gearvmstore.GearVM.repository;

import com.gearvmstore.GearVM.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Boolean existsByEmail(String email);

    Customer findByEmail(String email);
}
