package com.bootcamp.visit.service;

import com.bootcamp.visit.repository.VisitRepository;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import model.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VisitService {
    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    EurekaClient eurekaClient;

    @Scheduled(fixedDelay = 60000,initialDelay = 2000)
    public void setVisitStatus(){
        List<Visit> visitList=visitRepository.findByStatusAndDateBefore(Visit.AGENDADA, Date.from(Instant.now()));
        for (int i = 0; i < visitList.size(); i++) {
            visitList.get(i).setStatus(Visit.PENDIENTE_FACTURA);
        }
    }

    public List<Visit> listAllUnbilled(){
        return visitRepository.findByStatus(Visit.PENDIENTE_FACTURA);
    }

    public List<Visit> listUnbilled(String customerId){
        return visitRepository.findByIdCustomerAndStatus(customerId, Visit.PENDIENTE_FACTURA);
    }

    public Visit setVisitBillLine(String visitId, String billLineId){
        Visit visit;
        Optional<Visit> optionalVisit=visitRepository.findById(visitId);
        if(optionalVisit.isPresent()){
            visit=optionalVisit.get();
            visit.setIdBillLine(billLineId);
            visit.setStatus(Visit.FACTURADA);
            return visitRepository.save(visit);
        }
        return null;
    }

    public Visit findByBillLine(String billLineId){
        Optional<Visit> optionalVisit=visitRepository.findByIdBillLine(billLineId);
        if(optionalVisit.isPresent()){
            return optionalVisit.get();
        }
        return null;
    }

    public Visit save(Visit visit){
        return visitRepository.save(visit);
    }

    public Visit findById(String id){
        Optional<Visit> optionalVisit=visitRepository.findById(id);
        if(optionalVisit.isPresent()){
            return optionalVisit.get();
        }
        return null;
    }
    
    public List<Visit> findAll(){
        return visitRepository.findAll();
    }
    
    public Visit insert(Visit visit){
        if(visit.getId()==null){
            return visitRepository.save(visit);
        }
        return null;
    }

    public Visit update(Visit visit){
        return visitRepository.save(visit);
    }

    public void delete(Visit visit){
        Application a=eurekaClient.getApplication("BILLS");
        String url=a.getInstances().get((int)(Math.random()*a.getInstances().size())).getHomePageUrl();
        ResponseEntity<String> response
                = new RestTemplate().getForEntity(url+"/llamar", String.class);
        //Debemos borrar la línea de factura que incluya esta visita
        visitRepository.delete(visit);
    }

    public void delete(String idVisit){
        //Debemos borrar la línea de factura que incluya esta visita
        visitRepository.deleteById(idVisit);
    }
}
