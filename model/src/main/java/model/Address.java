package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Address {
    @Id
    private String id;
    private String street;
    private String number;
    private String town;
    private String state;
    private String customer_id;
}
