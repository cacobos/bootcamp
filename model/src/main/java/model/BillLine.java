package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;


@Data @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class BillLine {
    private String id;
    private String visitId;
    private float amount;
}
