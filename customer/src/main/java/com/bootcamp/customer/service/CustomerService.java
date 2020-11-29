package com.bootcamp.customer.service;

import com.bootcamp.customer.repository.AddressRepository;
import com.bootcamp.customer.repository.CustomerRepository;
import model.Address;
import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll(){
        List<Customer> customers=customerRepository.findAll();
        for (int i = 0; i < customers.size(); i++) {
            customers.get(i).setAddressList(addressRepository.findByCustomerId(customers.get(i).getId()));
        }
        return customers;
    }

    public Customer findById(String id){
        if(customerRepository.findById(id).isPresent()) {
            Customer customer = customerRepository.findById(id).get();
            customer.setAddressList(addressRepository.findByCustomerId(id));
            return customer;
        }else {
            return null;
        }
    }

    public Customer findByAddressId(String id){
        if(customerRepository.findByAddressId(id).isPresent()) {
            Customer customer = customerRepository.findByAddressId(id).get();
            customer.setAddressList(addressRepository.findByCustomerId(id));
            return customer;
        }else {
            return null;
        }
    }

    public List<Customer> findByState(String state){
        List<Address> addressList=addressRepository.findByStateLike(state);
        List<Customer> customers=new ArrayList<>();
        for (int i = 0; i < addressList.size(); i++) {
            if(customerRepository.findByAddressId(addressList.get(i).getId()).isPresent()) {
                customers.add(customerRepository.findByAddressId(addressList.get(i).getId()).get());
            }
        }
        return customers;
    }

    public List<Customer> findByName(String name){
        return customerRepository.findByNameLike(name);
    }

    public String getCustomerStatus(String customerId){
        Customer customer=findById(customerId);
        return "Hay que implementarlo!!";
    }
}
