package edu.fudanselab.trainticket.entity;

import lombok.Data;

/**
 * @author fdse
 */
@Data
public class InsertConsignRecordResult {
    private boolean status;

    private String message;

    public InsertConsignRecordResult(){
        //Default Constructor
    }

}
