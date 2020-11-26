package com.bootcamp.bill.controller;

import com.bootcamp.bill.service.BillService;
import model.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Controller
public class BillController {
    @Autowired
    private BillService billService;

    @GetMapping("/bills")
    public ResponseEntity<Flux<Bill>> listarBills(){
        return ResponseEntity.ok(billService.findAll());
    }

    @PostMapping("/bills")
    public ResponseEntity<Bill> insertar(@RequestBody @Valid Bill bill){
        Mono<Bill> bdBill=billService.save(bill);
        if(bdBill==null){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        return ResponseEntity.ok(bdBill.block());
    }

    @GetMapping("/bills/{id}")
    public ResponseEntity<Bill> findById(@PathVariable(name = "id") String id){
        Mono<Bill> bdBill=billService.findById(id);
        return ResponseEntity.ok(bdBill.block());
    }
}
