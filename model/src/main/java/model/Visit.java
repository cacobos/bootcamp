package model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity @Data
public class Visit {
    public final static int AGENDADA=1;
    public final static int PENDIENTE_FACTURA=2;
    public final static int FACTURADA=3;
    @Id
    private String id;
    private int status;
    private String remark;
    @Column(name = "customer_id")
    private String idCustomer;
    @Column(name = "bill_id")
    private String idBill;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
