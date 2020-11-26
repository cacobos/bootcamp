package com.bootcamp.visit.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Visit {
    public final static int FACTURADO=1;
    public final static int NO_FACTURADO=2;
    @Id
    private String id;
    private int status;
    private String remark;
}
