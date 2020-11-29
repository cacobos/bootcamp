package com.bootcamp.payment;

import com.bootcamp.payment.service.PaymentService;
import model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @GetMapping("/payments")
    public Flux<Payment> listAll(){
        return paymentService.findAll();
    }

    @PostMapping("/payments")
    public Mono<Payment> insert(@RequestBody @Valid Payment payment){
        return paymentService.insert(payment);
    }
}
