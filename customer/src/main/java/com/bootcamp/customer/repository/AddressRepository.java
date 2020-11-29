package com.bootcamp.customer.repository;

import model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,String> {

    List<Address> findByCustomerId(String idCostumer);
    List<Address> findByStateLike(String state);
}
