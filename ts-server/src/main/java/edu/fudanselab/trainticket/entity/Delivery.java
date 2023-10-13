package edu.fudanselab.trainticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
@GenericGenerator(name = "delivery-jpa-uuid", strategy = "org.hibernate.id.UUIDGenerator")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Delivery {
    public Delivery() {
        //Default Constructor
    }

    @Id
    @GeneratedValue(generator = "delivery-jpa-uuid")
    @Column(length = 36)
    private String id;

    private UUID orderId;
    private String foodName;
    private String storeName;
    private String stationName;
}
