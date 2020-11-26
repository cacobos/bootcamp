package com.bootcamp.customer.model;

import javax.persistence.*;

@Entity
public class Customer {
    @Id
    private String id;
    private String name;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address", cascade = CascadeType.ALL)
    private Address address;
}
