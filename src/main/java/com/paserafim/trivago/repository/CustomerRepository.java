package com.paserafim.trivago.repository;

import com.paserafim.trivago.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //List<Customer> findAll();
}
