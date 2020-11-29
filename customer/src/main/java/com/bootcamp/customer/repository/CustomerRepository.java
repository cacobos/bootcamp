package com.bootcamp.customer.repository;

import model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    List<Customer> findAll();
    Optional<Customer> findById(String id);
    Optional<Customer> findByAddressId(String id);
    List<Customer> findByNameLike(String name);
    //List<Customer> findByProvinciaLike(String provincia);
}
