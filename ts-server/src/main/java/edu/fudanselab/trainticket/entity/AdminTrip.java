package edu.fudanselab.trainticket.entity;

import edu.fudanselab.trainticket.entity.Route;
import edu.fudanselab.trainticket.entity.TrainType;
import lombok.Data;

/**
 * @author fdse
 */
@Data
public class AdminTrip {
    private Trip trip;
    private TrainType trainType;
    private Route route;

    public AdminTrip(){
        //Default Constructor
    }

}
