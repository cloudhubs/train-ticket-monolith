package edu.fudanselab.trainticket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripOrderInfo {
    private String orderId;
    private String tripId;
}
