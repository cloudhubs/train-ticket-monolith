package edu.fudanselab.trainticket.entity;

import edu.fudanselab.trainticket.entity.Trip;
import edu.fudanselab.trainticket.util.StringUtils;
import lombok.Data;

import java.util.Date;

/**
 * @author fdse
 */
@Data
public class Travel {

    private Trip trip;

    private String startPlace;

    private String endPlace;

    private String departureTime;

    public Travel(){
        //Default Constructor
    }
}
