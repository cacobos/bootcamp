package model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity @Data
public class Address {
    @Id
    private String id;
    private String street;
    private String number;
    private String town;
    private String state;
    private String customerId;
}
