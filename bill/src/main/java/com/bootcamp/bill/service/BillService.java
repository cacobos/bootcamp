package com.bootcamp.bill.service;

import com.bootcamp.bill.repository.BillRepository;
import model.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;

    public Mono<Bill> save(Bill bill){
        if(bill.getId()==null){
            return billRepository.save(bill);
        }else{
            return null;
        }
    }

    public Mono<Bill> findById(String id){
        return billRepository.findById(id);
    }

    public Flux<Bill> findAll(){
        return billRepository.findAll();
    }
}
