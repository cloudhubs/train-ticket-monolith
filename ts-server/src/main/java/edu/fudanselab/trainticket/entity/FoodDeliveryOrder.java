package edu.fudanselab.trainticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import edu.fudanselab.trainticket.entity.Food;

@Data
@Entity
@GenericGenerator(name = "food-delivery-jpa-uuid", strategy = "uuid")
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class FoodDeliveryOrder {

    @Id
    @GeneratedValue(generator = "food-delivery-jpa-uuid")
    @Column(length = 36)
    private String id;

    private String stationFoodStoreId;

    @ElementCollection(targetClass = Food.class)
    private List<Food> foodList;

    private String tripId;

    private int seatNo;

    private String createdTime;

    private String deliveryTime;

    private double deliveryFee;
}
