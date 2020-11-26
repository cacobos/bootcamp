package com.bootcamp.bill.repository;

import model.Bill;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BillRepository extends ReactiveMongoRepository<Bill, String> {
    Flux<Bill> findAll();

}
