package com.bootcamp.customer.repository;

import model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAll();
    Optional<Customer> findById(Long id);
    Optional<Customer> findByAddressId(Long id);
    List<Customer> findByNameLike(String name);
    void deleteById(Long id);
    //List<Customer> findByProvinciaLike(String provincia);
}
