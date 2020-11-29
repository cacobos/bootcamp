package com.bootcamp.bill.service;


import com.bootcamp.bill.repository.BillRepository;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;



    @Autowired
    EurekaClient eurekaClient;

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

    //Creamos una tarea que se ejecutará a las 00:02 de cada día 1 de mes y crea las facturas con las visitas pendientes
    //@Scheduled(cron = "0 1 0 02 * ?", zone = "Europe/Paris")
    //De momento, creamos facturas cada minuto para poder probar
    @Scheduled(initialDelay = 12000, fixedDelay = 6000)
    public void facturar(){
        Application a=eurekaClient.getApplication("CUSTOMERS");
        String url=a.getInstances().get((int)(Math.random()*a.getInstances().size())).getHomePageUrl();
        ResponseEntity<String> response
                = new RestTemplate().getForEntity(url+"/customers", String.class);
        List<Customer> customerList= JsonUtility.parseJSONList(response.getBody());
        for (int i = 0; i < customerList.size(); i++) {
            createCustomerBill(customerList.get(i).getId());
        }
    }



    private void createCustomerBill(String customerId) {
        float amount=0;
        Application a=eurekaClient.getApplication("VISITS");
        String url=a.getInstances().get((int)(Math.random()*a.getInstances().size())).getHomePageUrl();
        ResponseEntity<String> response
                = new RestTemplate().getForEntity(url+"/visits/unbilled", String.class, customerId);
        List<Visit> visitList= JsonUtility.parseJSONList(response.getBody());
        if(!visitList.isEmpty()) {
            Bill bill=new Bill();
            bill.setIdCliente(customerId);
            bill.setPayment(((int)(Math.random()*3))+1);
            bill=billRepository.save(bill).block();
            for (Visit visit : visitList) {
                BillLine billLine = new BillLine();
                billLine.setAmount((float) (Math.random() * 40) + 20);
                amount+=billLine.getAmount();
                billLine.setVisitId(visit.getId());
                bill.getBillLines().add(billLine);
                visit.setIdBill(bill.getId());
                url = a.getInstances().get((int) (Math.random() * a.getInstances().size())).getHomePageUrl();
                new RestTemplate().put(url + "/visits", visit, String.class, visit.getId());
            }
            bill.setAmount(amount);
            bill=billRepository.save(bill).block();
            for (int i = 0; i < bill.getPayment(); i++) {
                Payment payment=new Payment();
                payment.setBillId(bill.getId());
                payment.setStatus(Payment.PENDIENTE);
                payment.setExpiring(Date.from(ZonedDateTime.now().plusMonths(i+1).toInstant()));
                savePayment(payment);
            }
        }
    }

    private void savePayment(Payment payment) {
        Application a=eurekaClient.getApplication("PAYMENTS");
        String url=a.getInstances().get((int)(Math.random()*a.getInstances().size())).getHomePageUrl();
        ResponseEntity<String> response
                = new RestTemplate().postForEntity(url+"/payments", payment, String.class);
    }


    public Mono<Void> deleteById(String id){
        return billRepository.deleteById(id);
    }
}
